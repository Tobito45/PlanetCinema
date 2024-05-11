package com.example.planetcinema.layout

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planetcinema.AppViewModelProvider
import com.example.planetcinema.R
import com.example.planetcinema.swipe.CreateSwipeAction
import com.example.planetcinema.view.FilterViewModel
import com.example.planetcinema.view.SwapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeableActionsBox


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwappingCard(orientation : Int,
                 viewSwapModel: SwapViewModel = viewModel(factory = AppViewModelProvider.Factory),
                 viewFilterModel: FilterViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val rangeSliderState = remember {
        RangeSliderState(
            activeRangeStart = viewFilterModel.uiState.filmRange.start,
            activeRangeEnd = viewFilterModel.uiState.filmRange.endInclusive,
            valueRange = viewFilterModel.uiState.filmRange,
        )
    }

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        SwappingCardPortrait(viewSwapModel = viewSwapModel, viewFilterModel = viewFilterModel,
            scope = coroutineScope,
            sheetState = sheetState,
            sliderState = rangeSliderState,
            onSwapRight = {
                coroutineScope.launch {
                    viewSwapModel.checkFilm(viewFilterModel::filmsFilter)
                }
            },
            onSwapLeft = { viewSwapModel.generateNewFilm(viewFilterModel::filmsFilter) }
        )
    } else {
        SwappingCardLandScape(viewSwapModel = viewSwapModel, viewFilterModel = viewFilterModel,
            scope = coroutineScope,
            sheetState = sheetState,
            onSwapRight = {
                coroutineScope.launch {
                    viewSwapModel.checkFilm()
                }
            },
            sliderState = rangeSliderState,
            onSwapLeft = { viewSwapModel.generateNewFilm() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwappingCardLandScape(viewSwapModel: SwapViewModel,
                                  viewFilterModel: FilterViewModel,
                                  sheetState: SheetState,
                                  sliderState: RangeSliderState,
                                  scope: CoroutineScope,
                                  onSwapRight : () -> Unit,
                                  onSwapLeft : () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize(),
    ) {
        val mLocalContext = LocalContext.current

        val left = CreateSwipeAction(
            OnSwipe = {
                onSwapLeft()
                Toast.makeText(mLocalContext, "Next film", Toast.LENGTH_SHORT).show()
            },
            background = Color(0xFF640000)
        )
        val right = CreateSwipeAction(
            OnSwipe = {
                onSwapRight()
                Toast.makeText(mLocalContext, "Film saved", Toast.LENGTH_SHORT).show()
            },
            background = Color(0xFF006400)

        )

        Icon(
            painter = painterResource(id = R.drawable.finger_left),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.1f)
                .fillMaxHeight()
                .padding(8.dp)
        )
        SwipeableActionsBox(
            swipeThreshold = 100.dp,
            startActions = listOf(left),
            endActions = listOf(right),
            backgroundUntilSwipeThreshold = Color.Transparent,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(0.88f)
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.95f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF3E3F3F))
                ) {
                    BasicAsyncImage(
                        url = viewSwapModel.uiState.actualFilm?.url ?: "https://static.thenounproject.com/png/1527904-200.png",
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(0.9f)
                            .padding(8.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier =
                        Modifier
                            .fillMaxHeight(0.8f)
                            .fillMaxWidth(1f)
                            .padding(start = 15.dp)
                    ) {
                        TextInfoFilm(
                            filmName = viewSwapModel.uiState.actualFilm?.name ?: "None film",
                            filmAutor =  viewSwapModel.uiState.actualFilm?.autor ?: "#???#",
                            filmMark = if (viewSwapModel.uiState.actualFilm?.mark.toString() == "null") "?" else viewSwapModel.uiState.actualFilm?.mark.toString(),
                            sizeMainText = 25,
                            sizeSmallText = 20,
                            smallTextModifier = Modifier.padding(top = 10.dp),
                            smallRowModifier = Modifier
                                .fillMaxSize(0.7f)
                                .padding(top = 10.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                        .background(Color(0xFF2F3030))
                        .align(Alignment.CenterHorizontally)
                ) {

                }
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.finger_right),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
        )
    }
    if (viewFilterModel.uiState.showBottomSheet) {
        FilterSheet(viewModel = viewFilterModel, sheetState = sheetState, scope = scope, rangeSliderState = sliderState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwappingCardPortrait(viewSwapModel: SwapViewModel,
                                 viewFilterModel: FilterViewModel,
                                 sheetState: SheetState,
                                 scope: CoroutineScope,
                                 sliderState: RangeSliderState,
                                 onSwapRight : () -> Unit,
                                 onSwapLeft : () -> Unit)
{
    val mLocalContext = LocalContext.current

    val left = CreateSwipeAction(
        OnSwipe = {
            onSwapLeft()
            Toast.makeText(mLocalContext, "Next film", Toast.LENGTH_SHORT).show()
                  },
                background = Color(0xFF640000)
    )
    val right = CreateSwipeAction(
        OnSwipe = {
            onSwapRight()
            Toast.makeText(mLocalContext, "Film saved", Toast.LENGTH_SHORT).show()
                  },
        background = Color(0xFF006400)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp, top = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SwipeableActionsBox(
            swipeThreshold = 100.dp,
            startActions = listOf(left),
            endActions = listOf(right),
            backgroundUntilSwipeThreshold = Color.Transparent,
        ) {
           Column (horizontalAlignment = Alignment.CenterHorizontally) {
           BasicAsyncImage(
               url = viewSwapModel.uiState.actualFilm?.url ?: "https://static.thenounproject.com/png/1527904-200.png",
               modifier = Modifier
                   .fillMaxWidth(0.8f)
                   .fillMaxHeight(0.75f)
                   .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
           )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.5f)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(Color(0xFF3E3F3F))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    TextInfoFilm(
                        filmName = viewSwapModel.uiState.actualFilm?.name ?: "None film",
                        filmAutor =  viewSwapModel.uiState.actualFilm?.autor ?: "#???#",
                        filmMark = if (viewSwapModel.uiState.actualFilm?.mark.toString() == "null") "?" else viewSwapModel.uiState.actualFilm?.mark.toString(),
                        sizeMainText = 20,
                        sizeSmallText = 15,
                        smallTextHorizontalArrangement = Arrangement.End,
                        smallRowModifier = Modifier
                            .fillMaxSize(),
                        )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .fillMaxHeight(0.2f)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(Color(0xFF2F3030))
            )
           }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(painter = painterResource(id = R.drawable.finger_left),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight(0.8f)
                    .padding(start = 8.dp))
            Icon(painter = painterResource(id = R.drawable.finger_right),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight(0.8f)
                    .padding(end = 8.dp)
            )
        }
    }
    if (viewFilterModel.uiState.showBottomSheet) {
        FilterSheet(viewModel = viewFilterModel, sheetState = sheetState, scope = scope, rangeSliderState = sliderState)
    }
}


