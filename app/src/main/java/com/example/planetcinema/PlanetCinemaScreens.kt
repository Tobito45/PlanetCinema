package com.example.planetcinema

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planetcinema.layout.Header
import com.example.planetcinema.layout.NavBar
import com.example.planetcinema.layout.SquareBackgroundHeader
import com.example.planetcinema.layout.SwappingCard
import com.example.planetcinema.layout.UserListCard
import com.example.planetcinema.layout.WheelCard


enum class PlaneCinemaScreen() {
    Swapping,
    Wheel,
    UserList
}

@Composable
fun PlanetCinemaApp(
    navController: NavHostController = rememberNavController()
) {
    //val backStackEntry by navController.currentBackStackEntryAsState()
    //val currentScreen = PlaneCinemaScreen.valueOf(
    //    backStackEntry?.destination?.route ?: PlaneCinemaScreen.Start.name
    //)

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
    }
}


@Composable
fun SwapScreen(
    selectedItem : Int,
    navController: NavHostController,
) {
    val orientation = LocalConfiguration.current.orientation

    SquareBackgroundHeader(orientation, 0);
    Header("Discover");
    SwappingCard(orientation)
    NavBar(selectedItem, orientation, navController)
}

@Composable
fun WheelScreen(
    selectedItem : Int,
    navController: NavHostController,
) {
    val orientation = LocalConfiguration.current.orientation

    SquareBackgroundHeader(orientation, 1);
    Header("Select a movie");
    WheelCard(orientation)
    NavBar(selectedItem, orientation, navController)
}

@Composable
fun UserListScreen(
    selectedItem : Int,
    navController: NavHostController,
) {
    val orientation = LocalConfiguration.current.orientation

    SquareBackgroundHeader(orientation, 2);
    Header("All movies");
    UserListCard(orientation)
    NavBar(selectedItem, orientation, navController)
}