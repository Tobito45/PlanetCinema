package com.example.planetcinema.starters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.planetcinema.R
import com.example.planetcinema.layout.EditFilmCard
import com.example.planetcinema.layout.Header
import com.example.planetcinema.layout.NavBar
import com.example.planetcinema.layout.SquareBackgroundHeader
import com.example.planetcinema.layout.SwappingCard
import com.example.planetcinema.layout.UserListCard
import com.example.planetcinema.layout.WheelCard
import com.example.planetcinema.view.AppViewModelProvider
import com.example.planetcinema.view.EditViewModel
import com.example.planetcinema.view.FilterViewModel
import com.example.planetcinema.view.SwapViewModel
import com.example.planetcinema.view.UserListViewModel
import com.example.planetcinema.view.WheelViewModel


enum class PlaneCinemaScreen() {
    Swapping,
    Wheel,
    UserList,
    Edit
}

@Composable
fun PlanetCinemaApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = PlaneCinemaScreen.Swapping.name,
    ) {
        composable(route = PlaneCinemaScreen.Swapping.name) {
            SwapScreen(PlaneCinemaScreen.Swapping.ordinal, navController)
        }
        composable(route = PlaneCinemaScreen.Wheel.name) {
            WheelScreen(PlaneCinemaScreen.Wheel.ordinal, navController)
        }
        composable(route = PlaneCinemaScreen.UserList.name) {
            UserListScreen(PlaneCinemaScreen.UserList.ordinal, navController)
        }
        composable(route = "${PlaneCinemaScreen.Edit.name}/{filmId}",
            arguments = listOf(navArgument(name = "filmId") { type = NavType.StringType })) {
                backStackEntry ->
            val filmId = backStackEntry.arguments?.getString("filmId")?.toIntOrNull() ?: 0
            EditScreen(navController, filmId)
        }
    }
}


@Composable
fun SwapScreen(
    selectedItem : Int,
    navController: NavHostController,
    viewSwapModel: SwapViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewFilterModel: FilterViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val orientation = LocalConfiguration.current.orientation

    SquareBackgroundHeader(orientation = orientation, menu = 0);
    Header(panelName = stringResource(R.string.discover), onIconClick = {viewFilterModel.resetUiState(active = true)})
    SwappingCard(orientation = orientation, viewSwapModel = viewSwapModel, viewFilterModel = viewFilterModel)
    NavBar(selectedItem, orientation, navController)
}

@Composable
fun WheelScreen(
    selectedItem : Int,
    navController: NavHostController,
    viewFilterModel: FilterViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewWheelModel: WheelViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val orientation = LocalConfiguration.current.orientation

    SquareBackgroundHeader(orientation, 1);
    Header(stringResource(R.string.select_a_movie), onIconClick = {viewFilterModel.resetUiState(active = true)})
    WheelCard(orientation = orientation,viewWheelModel = viewWheelModel, viewFilterModel = viewFilterModel)
    NavBar(selectedItem, orientation, navController)
}

@Composable
fun UserListScreen(
    selectedItem : Int,
    navController: NavHostController,
    viewUserModel: UserListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    viewFilterModel: FilterViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
    val orientation = LocalConfiguration.current.orientation
    val coroutineScope = rememberCoroutineScope()


    SquareBackgroundHeader(orientation, 2);
    Header(stringResource(R.string.all_movies), onIconClick = {viewFilterModel.resetUiState(active = true)})
    UserListCard(orientation = orientation, viewUserModel = viewUserModel, viewFilterModel = viewFilterModel,
        coroutineScope = coroutineScope,
        navController = navController)
    NavBar(selectedItem, orientation, navController)
}

@Composable
fun EditScreen(
    navController: NavHostController,
    filmId : Int,
    viewModel: EditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val orientation = LocalConfiguration.current.orientation
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.setBasicParameters(filmId)
    }


    EditFilmCard(orientation = orientation,
        onNavigateUp = { navController.navigateUp() },
        viewModel = viewModel,
        scope = coroutineScope
        )
}