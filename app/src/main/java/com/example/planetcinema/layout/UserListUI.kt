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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planetcinema.PlaneCinemaScreen
import com.example.planetcinema.data.Film
import com.example.planetcinema.view.UserListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.ceil


@Composable
fun UserListCard(orientation: Int,
                 viewModel: UserListViewModel,
                 navController: NavController,
                 coroutineScope: CoroutineScope
) {

    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        UserListScrollPortrait(viewModel, coroutineScope, navController)
    } else {
        UserListScrollLandscape(viewModel, coroutineScope, navController)
    }
}

@Composable
private fun UserListScrollPortrait(viewModel: UserListViewModel, scope : CoroutineScope, navController: NavController) {

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
            items(viewModel.uiState.filmList.size + 1) { index ->
                    UserListElement(
                        film = if (index != viewModel.uiState.filmList.size) viewModel.uiState.filmList[index]
                                else Film(name = "#???#", autor = "#???#", mark = -1.0f, url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTXtOwPfVadN_I7YzkGc_CzjwOTNOvzjNxsg&usqp=CAU") ,
                        onCheckedValue = {
                            if (index != viewModel.uiState.filmList.size) {
                                scope.launch {
                                    viewModel.watchFilm(viewModel.uiState.filmList[index])
                                }
                            }
                        },
                        onImageClick = { navController.navigate(PlaneCinemaScreen.Edit.name) },
                        isChecked = if(index != viewModel.uiState.filmList.size) viewModel.uiState.watchedFilms[index] else false,
                        hasChecker = index != viewModel.uiState.filmList.size,
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
}


@Composable
private fun UserListScrollLandscape(viewModel: UserListViewModel, scope : CoroutineScope, navController: NavController) {
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
            val result = ceil(viewModel.uiState.filmList.size.toFloat() / 2.0f).toInt()
            items(if (viewModel.uiState.filmList.size  % 2 == 0) result + 1 else result) { index ->
                Row {
                    UserListElement(
                        film = if (index < result) viewModel.uiState.filmList[index]
                        else Film(
                            name = "#???#",
                            autor = "#???#",
                            mark = -1.0f,
                            url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTXtOwPfVadN_I7YzkGc_CzjwOTNOvzjNxsg&usqp=CAU"
                        ),
                        onCheckedValue = {
                            if (index < result) {
                                scope.launch {
                                    viewModel.watchFilm(viewModel.uiState.filmList[index])
                                }
                            }
                        },
                        onImageClick = { navController.navigate(PlaneCinemaScreen.Edit.name) },
                        isChecked = if (index < result) viewModel.uiState.watchedFilms[index] else false,
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
                        val secondIndex = viewModel.uiState.filmList.size - 1 - index
                        UserListElement(
                            film = if (secondIndex != index) viewModel.uiState.filmList[secondIndex]
                            else Film(
                                name = "#???#",
                                autor = "#???#",
                                mark = -1.0f,
                                url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTXtOwPfVadN_I7YzkGc_CzjwOTNOvzjNxsg&usqp=CAU"
                            ),
                            onCheckedValue = {
                                if (secondIndex != index) {
                                    scope.launch {
                                        viewModel.watchFilm(viewModel.uiState.filmList[secondIndex])
                                    }
                                }
                            },
                            onImageClick = { navController.navigate(PlaneCinemaScreen.Edit.name) },
                            isChecked = if (secondIndex != index) viewModel.uiState.watchedFilms[secondIndex] else false,
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
}

@Composable
fun UserListElement(film : Film,
                    onCheckedValue : (Film) -> Unit,
                    onImageClick : () -> Unit,
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
        //IconButton(onClick = onImageClick,
         //          modifier = Modifier.fillMaxWidth(0.4f)
        //               .fillMaxHeight()
         //           ) {
            BasicAsyncImage(
                url = film.url,
                modifier = imageModifier.clickable { onImageClick() }
            )
      //  }
        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)) {
            TextInfoFilm(
                filmName = film.name,
                filmAutor = film.autor,
                filmMark = film.mark.toString(),
                sizeMainText = 18,
                sizeSmallText = 15,
                smallTextModifier = smallTextModifier,
                smallRowModifier = markRowModifier)
        }

        if(hasChecker) {
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

