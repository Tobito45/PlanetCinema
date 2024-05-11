package com.example.planetcinema.layout

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planetcinema.PlaneCinemaScreen
import com.example.planetcinema.data.Film
import com.example.planetcinema.view.FilterViewModel
import com.example.planetcinema.view.UserListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.ceil


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListCard(orientation: Int,
                 viewUserModel: UserListViewModel,
                 viewFilterModel: FilterViewModel,
                 navController: NavController,
                 coroutineScope: CoroutineScope
) {
    val sheetState = rememberModalBottomSheetState()
    val rangeSliderState = remember {
        RangeSliderState(
            activeRangeStart = viewFilterModel.uiState.filmRange.start,
            activeRangeEnd = viewFilterModel.uiState.filmRange.endInclusive,
            valueRange = viewFilterModel.uiState.filmRange,
        )
    }

    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        UserListScrollPortrait(
            viewUserModel = viewUserModel,
            viewFilterModel = viewFilterModel,
            scope =  coroutineScope,
            rangeSliderState = rangeSliderState,
            sheetState = sheetState,
            navController = navController)
    } else {
        UserListScrollLandscape(
            viewUserModel = viewUserModel,
            viewFilterModel = viewFilterModel,
            scope =  coroutineScope,
            rangeSliderState = rangeSliderState,
            sheetState = sheetState,
            navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserListScrollPortrait(
    viewUserModel: UserListViewModel,
    viewFilterModel: FilterViewModel,
    rangeSliderState: RangeSliderState,
    sheetState: SheetState,
    scope : CoroutineScope,
    navController: NavController)
{

    Column {
        Spacer(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
        ) {
            items(viewUserModel.uiState.filmList.size + 1) { index ->
                UserListElement(
                        film = if (index != viewUserModel.uiState.filmList.size) viewUserModel.uiState.filmList[index]
                                else Film(name = "#???#", autor = "#???#", mark = -1.0f, url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTXtOwPfVadN_I7YzkGc_CzjwOTNOvzjNxsg&usqp=CAU") ,
                        onCheckedValue = {
                            if (index != viewUserModel.uiState.filmList.size) {
                                scope.launch {
                                    viewUserModel.watchFilm(viewUserModel.uiState.filmList[index], viewFilterModel::filmsFilter)
                                }
                            }
                        },
                        onImageClick = {
                            if(!(index != viewUserModel.uiState.filmList.size) || viewUserModel.uiState.filmList[index].isCreated)
                                navController.navigate("${PlaneCinemaScreen.Edit.name}/" +
                                    "${if(index != viewUserModel.uiState.filmList.size) viewUserModel.uiState.filmList[index].id else -1 }") },
                        isChecked = if(index != viewUserModel.uiState.filmList.size) viewUserModel.uiState.watchedFilms[index] else false,
                        hasChecker = index != viewUserModel.uiState.filmList.size,
                        genarelModifier =  Modifier
                            .fillMaxWidth(0.9f)
                            .height(170.dp)
                            .padding(top = 20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF3E3F3F)),
                        imageModifier = Modifier
                            .fillMaxWidth(0.4f)
                            .padding(10.dp),
                        smallTextModifier = Modifier.padding(top = 10.dp),
                        markRowModifier = Modifier
                            .fillMaxSize(0.7f)
                            .padding(top = 18.dp)
                    )
            }
        }
    }
    if (viewFilterModel.uiState.showBottomSheet) {
        FilterSheet(viewModel = viewFilterModel, sheetState = sheetState, scope = scope, rangeSliderState = rangeSliderState,
                    onCloseButton = {
                        scope.launch {
                        viewUserModel.getAllFilm(viewFilterModel::filmsFilter)
                    } })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserListScrollLandscape(
    viewUserModel: UserListViewModel,
    viewFilterModel: FilterViewModel,
    rangeSliderState: RangeSliderState,
    sheetState: SheetState,
    scope : CoroutineScope,
    navController: NavController)
{
    Column {
        Spacer(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .padding(horizontal = 20.dp)
        ) {
            val result = ceil(viewUserModel.uiState.filmList.size.toFloat() / 2.0f).toInt()
            items(if (viewUserModel.uiState.filmList.size  % 2 == 0) result + 1 else result) { index ->
                Row {
                    UserListElement(
                        film = if (index < result) viewUserModel.uiState.filmList[index]
                        else Film(
                            name = "#???#",
                            autor = "#???#",
                            mark = -1.0f,
                            url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTXtOwPfVadN_I7YzkGc_CzjwOTNOvzjNxsg&usqp=CAU"
                        ),
                        onCheckedValue = {
                            if (index < result) {
                                scope.launch {
                                    viewUserModel.watchFilm(viewUserModel.uiState.filmList[index])
                                }
                            }
                        },
                        onImageClick = {
                            if( !(index < result) || viewUserModel.uiState.filmList[index].isCreated)
                                navController.navigate("${PlaneCinemaScreen.Edit.name}/" +
                                    "${if(index < result) viewUserModel.uiState.filmList[index].id else -1 }")
                            },
                        isChecked = if (index < result) viewUserModel.uiState.watchedFilms[index] else false,
                        hasChecker = index < result,
                        genarelModifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(100.dp)
                            .padding(top = 20.dp, end = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF3E3F3F)),
                        imageModifier = Modifier
                            .fillMaxWidth(0.3f)
                            .padding(10.dp),
                        smallTextModifier = Modifier.padding(top = 2.dp),
                        markRowModifier = Modifier
                            .fillMaxSize(0.85f)
                            .padding(top = 2.dp)
                    )
                    if (index < result) {
                        val secondIndex = viewUserModel.uiState.filmList.size - 1 - index
                        UserListElement(
                            film = if (secondIndex != index) viewUserModel.uiState.filmList[secondIndex]
                            else Film(
                                name = "#???#",
                                autor = "#???#",
                                mark = -1.0f,
                                url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTXtOwPfVadN_I7YzkGc_CzjwOTNOvzjNxsg&usqp=CAU"
                            ),
                            onCheckedValue = {
                                if (secondIndex != index) {
                                    scope.launch {
                                        viewUserModel.watchFilm(viewUserModel.uiState.filmList[secondIndex])
                                    }
                                }
                            },
                            onImageClick = {
                                if(!(secondIndex != index) || viewUserModel.uiState.filmList[secondIndex].isCreated)
                                    navController.navigate("${PlaneCinemaScreen.Edit.name}/" +
                                        "${if(secondIndex != index) viewUserModel.uiState.filmList[secondIndex].id else -1 }")
                            },
                            isChecked = if (secondIndex != index) viewUserModel.uiState.watchedFilms[secondIndex] else false,
                            hasChecker = secondIndex != index,
                            genarelModifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(top = 20.dp, start = 10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF3E3F3F)),
                            imageModifier = Modifier
                                .fillMaxWidth(0.3f)
                                .padding(10.dp),
                            smallTextModifier = Modifier.padding(top = 2.dp),
                            markRowModifier = Modifier
                                .fillMaxSize(0.85f)
                                .padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
    FilterSheet(viewModel = viewFilterModel, sheetState = sheetState, scope = scope, rangeSliderState = rangeSliderState,
        onCloseButton = {
            scope.launch {
                viewUserModel.getAllFilm(viewFilterModel::filmsFilter)
            } })
}

@Composable
fun UserListElement(film : Film,
                    onCheckedValue : (Film) -> Unit,
                    onImageClick : (Film) -> Unit,
                    isChecked : Boolean,
                    hasChecker : Boolean = true,
                    genarelModifier : Modifier,
                    imageModifier : Modifier,
                    smallTextModifier : Modifier,
                    markRowModifier: Modifier
) {
    Row(
        modifier = genarelModifier
    ) {
        BasicAsyncImage(
            url = film.url,
            modifier = imageModifier.clickable { onImageClick(film) }
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
        ) {
            TextInfoFilm(
                filmName = film.name,
                filmAutor = film.autor,
                filmMark = film.mark.toString(),
                sizeMainText = 18,
                sizeSmallText = 15,
                smallTextModifier = smallTextModifier,
                smallRowModifier = markRowModifier
            )
        }

        if (hasChecker) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { onCheckedValue(film) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFF1F8E9)
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 20.dp)
            )
        }
    }
}

