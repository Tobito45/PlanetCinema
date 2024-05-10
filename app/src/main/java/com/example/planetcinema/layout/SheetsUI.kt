package com.example.planetcinema.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planetcinema.data.Film
import com.example.planetcinema.view.FilterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WheelBottomSheet (sheetState: SheetState,
                      scope: CoroutineScope,
                      films : List<Film>,
                      containFilm : (Film) -> Boolean,
                      onDismissRequest : () -> Unit,
                      onCloseButton : () -> Unit,
                      filmAdder : (Film) -> Unit
) {
    BasicSheet(
        sheetState = sheetState,
        scope = scope,
        onDismissRequest = onDismissRequest,
        onCloseButton = onCloseButton) {
        films.forEach { film ->
            BasicSheetCard {
                BasicSheetText(text = film.name)
                Button(onClick = { filmAdder(film) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3E3F3F),
                        disabledContainerColor = Color(0xFF181818),
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(imageVector = if(containFilm(film)) Icons.Filled.Done else Icons.Filled.Add, contentDescription = null,
                        tint = Color.White)
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
) {
    BasicSheet(
        sheetState = sheetState,
        scope = scope,
        onDismissRequest = {viewModel.resetUiState(active = false)} ,
        onCloseButton = {viewModel.resetUiState(active = false)} ) {
        BasicSheetCard {
            BasicSheetText(text = "Sort films by alphabetic of names")
            Checkbox(checked = viewModel.uiState.isAbcFilmSorted, onCheckedChange = {viewModel.resetUiState(
                isAbcFilmSorted = it)}, modifier = Modifier.padding(10.dp))
        }
        BasicSheetCard {
            BasicSheetText(text = "Sort films by alphabetic of authors")
            Checkbox(checked = viewModel.uiState.isAbcAutorSorted, onCheckedChange = {viewModel.resetUiState(
                isAbcAutorSorted = it)}, modifier = Modifier.padding(10.dp))
        }
        BasicSheetCard {
            BasicSheetText(text = "Sort films by marks")
            Column {
                val rangeStart = "%.2f".format(rangeSliderState.activeRangeStart).replace(",",".")
                val rangeEnd = "%.2f".format(rangeSliderState.activeRangeEnd).replace(",",".")
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
                        .padding(5.dp)
                )

                Text(text = "$rangeStart .. $rangeEnd", color = Color.White, modifier = Modifier.padding(10.dp))
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
        containerColor = Color(0xFF242424)
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
                    containerColor = Color(0xFF3E3F3F),
                    disabledContainerColor = Color(0xFF181818),
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
                    text = "Hide",
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
            .background(Color(0xFF1B1C1F))
    ) {
        content()
    }
}

@Composable
fun BasicSheetText(text : String) {
    Text(text = text, color = Color.White, modifier = Modifier.padding(10.dp))
}