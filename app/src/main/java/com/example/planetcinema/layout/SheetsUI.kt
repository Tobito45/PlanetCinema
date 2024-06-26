package com.example.planetcinema.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.SheetState
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.planetcinema.R
import com.example.planetcinema.data.film.Film
import com.example.planetcinema.ui.theme.ButtonBackGroundColor
import com.example.planetcinema.ui.theme.DisableButtonBackGroundColor
import com.example.planetcinema.ui.theme.SheetBackGroundColor
import com.example.planetcinema.ui.theme.SquareBackGroundColor
import com.example.planetcinema.view.FilterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WheelBottomSheet (sheetState: SheetState,
                      scope: CoroutineScope,
                      canBeShow : Boolean,
                      films : List<Film>,
                      containFilm : (Film) -> Boolean,
                      onDismissRequest : () -> Unit,
                      onCloseButton : () -> Unit,
                      filmAdder : (Film) -> Unit
) {
    if (canBeShow) {
        BasicSheet(
            sheetState = sheetState,
            scope = scope,
            onDismissRequest = onDismissRequest,
            onCloseButton = onCloseButton
        ) {
            films.forEach { film ->
                BasicSheetCard {
                    BasicSheetText(text = film.name)
                    Button(
                        onClick = { filmAdder(film) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonBackGroundColor,
                            disabledContainerColor = DisableButtonBackGroundColor,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(
                            imageVector = if (containFilm(film)) Icons.Filled.Done else Icons.Filled.Add,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet (viewModel: FilterViewModel,
                 sheetState: SheetState,
                 scope: CoroutineScope,
                 rangeSliderState : RangeSliderState,
                 onCloseButton: () -> Unit = {},
                 content: @Composable ColumnScope.() -> Unit = {}
) {
    BasicSheet(
        sheetState = sheetState,
        scope = scope,
        onDismissRequest = {
            viewModel.resetUiState(active = false)
            onCloseButton()
                           } ,
        onCloseButton = {
            viewModel.resetUiState(active = false)
            onCloseButton()
        } ) {
        content()
        BasicSheetCard {
            BasicSheetText(text = stringResource(R.string.sort_films_contains), modifier = Modifier.fillMaxSize(0.5f))
            BasicSheetTextField(value = viewModel.uiState.filmContainsWord,
                onValueChange =  { viewModel.resetUiState(filmContainsWord = it)})
        }
        BasicSheetCard {
            BasicSheetText(text = stringResource(R.string.sort_authors_contains), modifier = Modifier.fillMaxSize(0.5f))
            BasicSheetTextField(value = viewModel.uiState.autorContainsWord,
                onValueChange =  { viewModel.resetUiState(autorContainsWord = it)})
        }
        BasicSheetCard {
            BasicSheetText(text = stringResource(R.string.sort_films_by_marks), modifier = Modifier.fillMaxSize(0.5f))
            Column {
                val rangeStart = "%.1f".format(rangeSliderState.activeRangeStart).replace(",",".")
                val rangeEnd = "%.1f".format(rangeSliderState.activeRangeEnd).replace(",",".")
                val endInteractionSource = remember { MutableInteractionSource() }
                val colors: SliderColors = SliderDefaults.colors()
                RangeSlider(
                    state = rangeSliderState,
                    endThumb =  {
                        SliderDefaults.Thumb(
                            interactionSource = endInteractionSource,
                            colors = colors,
                            enabled = true
                        )
                        viewModel.resetUiState(filmRange = rangeStart.toFloat()..rangeEnd.toFloat())
                    },
                    modifier = Modifier
                        .semantics { contentDescription = "Localized Description" }
                        .padding(top = 5.dp, end = 15.dp)
                )

                Text(text = "$rangeStart .. $rangeEnd", color = Color.White, modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterUserListAndWheelSheet(viewModel: FilterViewModel,
                                sheetState: SheetState,
                                scope: CoroutineScope,
                                rangeSliderState : RangeSliderState,
                                onCloseButton: () -> Unit = {}
)  {
    if(viewModel.uiState.showBottomSheet) {
        FilterSheet(
            viewModel = viewModel,
            sheetState = sheetState,
            scope = scope,
            rangeSliderState = rangeSliderState,
            onCloseButton = onCloseButton
        ) {
            BasicSheetCard {
                BasicSheetText(stringResource(R.string.only_watched_films), modifier = Modifier.fillMaxSize(0.5f))
                BasicSheetCheckBox(checked = viewModel.uiState.isWatchedFilms,
                    onCheckedChange = { viewModel.resetUiState(isWatchedFilms = it, onlyNonWatchedFilms = false) })
            }
            BasicSheetCard {
                BasicSheetText(stringResource(R.string.only_non_watched_films), modifier = Modifier.fillMaxSize(0.5f))
                BasicSheetCheckBox(checked = viewModel.uiState.onlyNonWatchedFilms,
                    onCheckedChange = { viewModel.resetUiState(onlyNonWatchedFilms = it, isWatchedFilms = false) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicSheet(sheetState: SheetState,
               scope: CoroutineScope,
               onDismissRequest : () -> Unit,
               onCloseButton : () -> Unit,
               content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = SheetBackGroundColor
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            content()

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBackGroundColor,
                    disabledContainerColor = DisableButtonBackGroundColor,
                ),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onCloseButton()
                        }
                    }
                },
                modifier = Modifier.padding(top = 10.dp, bottom = 30.dp)
            ) {
                Text(
                    text = stringResource(R.string.hide),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BasicSheetCard(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SquareBackGroundColor)
    ) {
        content()
    }
}

@Composable
fun BasicSheetText(text : String, modifier: Modifier = Modifier) {
    Text(text = text, color = Color.White, modifier = modifier.padding(10.dp))
}

@Composable
fun BasicSheetCheckBox(checked : Boolean, onCheckedChange : (Boolean) -> Unit) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
    )
}

@Composable
fun BasicSheetTextField(value : String, onValueChange : (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp, end = 15.dp)
    )
}