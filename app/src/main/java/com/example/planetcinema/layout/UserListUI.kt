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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planetcinema.R
import com.example.planetcinema.data.film.Film
import com.example.planetcinema.starters.PlaneCinemaScreen
import com.example.planetcinema.ui.theme.ButtonBackGroundColor
import com.example.planetcinema.ui.theme.CheckBoxUserListColor
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
    navController: NavController) {

    Column {
        Spacer(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.12f)
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
                    else Film(
                        name = stringResource(R.string.error_argument),
                        autor = stringResource(R.string.error_argument),
                        mark = -1.0f,
                        url = stringResource(R.string.new_film_image)
                    ),
                    onCheckedValue = {
                        if (index != viewUserModel.uiState.filmList.size) {
                            if (viewUserModel.uiState.filmList[index].isWatched) {
                                scope.launch {
                                    viewUserModel.watchFilm(
                                        false,
                                        viewUserModel.uiState.filmList[index],
                                        viewFilterModel::filmsFilter
                                    )
                                }
                            } else
                                viewUserModel.actualizationDialog(
                                    true,
                                    mark = if (viewUserModel.uiState.filmList[index].userMark > 0)
                                        viewUserModel.uiState.filmList[index].userMark.toString()
                                            .replace(",", ".") else "",
                                    seletedFilm = viewUserModel.uiState.filmList[index]
                                )
                        }
                    },
                    onImageClick = {
                        if (!(index != viewUserModel.uiState.filmList.size) || viewUserModel.uiState.filmList[index].isCreated)
                            navController.navigate(
                                "${PlaneCinemaScreen.Edit.name}/" +
                                        "${if (index != viewUserModel.uiState.filmList.size) viewUserModel.uiState.filmList[index].id else -1}"
                            )
                    },
                    isChecked = if (index != viewUserModel.uiState.filmList.size) viewUserModel.uiState.watchedFilms[index] else false,
                    hasChecker = index != viewUserModel.uiState.filmList.size,
                    genarelModifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(170.dp)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ButtonBackGroundColor),
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
    PopUpsUserList(
        viewFilterModel = viewFilterModel,
        sheetState = sheetState,
        scope = scope,
        rangeSliderState = rangeSliderState,
        viewUserModel = viewUserModel
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserListScrollLandscape(
    viewUserModel: UserListViewModel,
    viewFilterModel: FilterViewModel,
    rangeSliderState: RangeSliderState,
    sheetState: SheetState,
    scope : CoroutineScope,
    navController: NavController) {
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
            items(if (viewUserModel.uiState.filmList.size % 2 == 0) result + 1 else result) { index ->
                Row {
                    UserListElement(
                        film = if (index < result) viewUserModel.uiState.filmList[index]
                        else Film(
                            name = stringResource(R.string.error_argument),
                            autor = stringResource(R.string.error_argument),
                            mark = -1.0f,
                            url = stringResource(R.string.new_film_image)
                        ),
                        onCheckedValue = {
                            if (index < result) {
                                if (viewUserModel.uiState.filmList[index].isWatched) {
                                    scope.launch {
                                        viewUserModel.watchFilm(
                                            watch = false,
                                            seletedFilm = viewUserModel.uiState.filmList[index],
                                            sorting = viewFilterModel::filmsFilter
                                        )
                                    }
                                } else
                                    viewUserModel.actualizationDialog(
                                        true,
                                        mark = if (viewUserModel.uiState.filmList[index].userMark > 0)
                                            viewUserModel.uiState.filmList[index].userMark.toString()
                                                .replace(",", ".") else "",
                                        seletedFilm = viewUserModel.uiState.filmList[index]
                                    )
                            }
                        },
                        onImageClick = {
                            if (!(index < result) || viewUserModel.uiState.filmList[index].isCreated)
                                navController.navigate(
                                    "${PlaneCinemaScreen.Edit.name}/" +
                                            "${if (index < result) viewUserModel.uiState.filmList[index].id else -1}"
                                )
                        },
                        isChecked = if (index < result) viewUserModel.uiState.watchedFilms[index] else false,
                        hasChecker = index < result,
                        genarelModifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(100.dp)
                            .padding(top = 20.dp, end = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ButtonBackGroundColor),
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
                                name = stringResource(R.string.error_argument),
                                autor = stringResource(R.string.error_argument),
                                mark = -1.0f,
                                url = stringResource(R.string.new_film_image)
                            ),
                            onCheckedValue = {
                                if (secondIndex != index) {
                                    if (viewUserModel.uiState.filmList[index].isWatched) {
                                        scope.launch {
                                            viewUserModel.watchFilm(
                                                watch = false,
                                                seletedFilm = viewUserModel.uiState.filmList[index],
                                                sorting = viewFilterModel::filmsFilter
                                            )
                                        }
                                    } else
                                        viewUserModel.actualizationDialog(
                                            true,
                                            mark = if (viewUserModel.uiState.filmList[secondIndex].userMark > 0)
                                                viewUserModel.uiState.filmList[secondIndex].userMark.toString()
                                                    .replace(",", ".") else "",
                                            seletedFilm = viewUserModel.uiState.filmList[secondIndex]
                                        )
                                }
                            },
                            onImageClick = {
                                if (!(secondIndex != index) || viewUserModel.uiState.filmList[secondIndex].isCreated)
                                    navController.navigate(
                                        "${PlaneCinemaScreen.Edit.name}/" +
                                                "${if (secondIndex != index) viewUserModel.uiState.filmList[secondIndex].id else -1}"
                                    )
                            },
                            isChecked = if (secondIndex != index) viewUserModel.uiState.watchedFilms[secondIndex] else false,
                            hasChecker = secondIndex != index,
                            genarelModifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(top = 20.dp, start = 10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(ButtonBackGroundColor),
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

    PopUpsUserList(
        viewFilterModel = viewFilterModel,
        sheetState = sheetState,
        scope = scope,
        rangeSliderState = rangeSliderState,
        viewUserModel = viewUserModel
    )
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
            var userMark = if(film.userMark < 0) "" else film.userMark
            TextInfoFilm(
                filmName = film.name,
                filmAutor = film.autor,
                filmMark = film.mark.toString(),
                userMark = userMark.toString(),
                isWatched = film.isWatched,
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
                    checkedColor = CheckBoxUserListColor
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListDialog(
    viewUserModel: UserListViewModel,
    onCloseDialog : () -> Unit,
    onConfirmDialog : () -> Unit
) {
    if (viewUserModel.uiState.dialogShow) {
        BasicAlertDialog(
            onDismissRequest = onCloseDialog
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.question_adding_mark),
                    )
                    TextField(value = viewUserModel.uiState.filmMarkValue,
                        onValueChange = { viewUserModel.actualizationDialog(active = true, mark = it)},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        maxLines = 1,
                        singleLine = true,
                        isError = viewUserModel.uiState.isDialogError,
                        label = { Text(text = stringResource(R.string.your_mark)) },
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = {
                                onConfirmDialog()
                                onCloseDialog()
                            },
                        ) {
                            Text(stringResource(R.string.confirm))
                        }
                        TextButton(
                            onClick = onCloseDialog,
                        ) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUpsUserList(
    viewFilterModel: FilterViewModel,
    sheetState: SheetState,
    scope: CoroutineScope,
    rangeSliderState: RangeSliderState,
    viewUserModel: UserListViewModel
) {
    FilterUserListAndWheelSheet(viewModel = viewFilterModel,
        sheetState = sheetState,
        scope = scope,
        rangeSliderState = rangeSliderState,
        onCloseButton = {
            scope.launch {
                viewUserModel.getAllFilm(viewFilterModel::filmsFilter)
            }
        })
    UserListDialog(viewUserModel = viewUserModel,
        onCloseDialog = { viewUserModel.actualizationDialog(false) },
        onConfirmDialog = {
            scope.launch {
                viewUserModel.watchFilm(
                    watch = true,
                    sorting = viewFilterModel::filmsFilter
                )
            }
        }
    )
}

