package com.example.planetcinema.layout

import android.content.res.Configuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.planetcinema.R
import com.example.planetcinema.swipe.CreateLeftSwipeAction
import com.example.planetcinema.swipe.CreateRightwipeAction
import com.example.planetcinema.ui.theme.ButtonBackGroundColor
import com.example.planetcinema.ui.theme.SwipeShadowColor
import com.example.planetcinema.view.FilterViewModel
import com.example.planetcinema.view.SwapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeableActionsBox


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwappingCard(orientation : Int,
                 viewSwapModel: SwapViewModel,
                 viewFilterModel: FilterViewModel
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

        val left = CreateLeftSwipeAction(onSwapLeft, mLocalContext)
        val right = CreateRightwipeAction(onSwapRight, mLocalContext)

        IconSwapping(
            imageId = R.drawable.finger_left,
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
                        .background(ButtonBackGroundColor)
                ) {
                    BasicAsyncImage(
                        url = viewSwapModel.uiState.actualFilm?.url ?: stringResource(R.string.error_image),
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
                            filmName = viewSwapModel.uiState.actualFilm?.name ?: stringResource(R.string.none_film),
                            filmAutor =  viewSwapModel.uiState.actualFilm?.autor ?: stringResource(R.string.error_argument),
                            filmMark = if (viewSwapModel.uiState.actualFilm?.mark.toString() == "null")
                                stringResource(R.string.question_mark) else viewSwapModel.uiState.actualFilm?.mark.toString(),
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
                        .background(SwipeShadowColor)
                        .align(Alignment.CenterHorizontally)
                ) {

                }
            }
        }

        IconSwapping(
            imageId = R.drawable.finger_right,
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

    val left = CreateLeftSwipeAction(onSwapLeft, mLocalContext)
    val right = CreateRightwipeAction(onSwapRight, mLocalContext)


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
               url = viewSwapModel.uiState.actualFilm?.url ?: stringResource(R.string.error_image),
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
                    .background(ButtonBackGroundColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    TextInfoFilm(
                        filmName = viewSwapModel.uiState.actualFilm?.name ?: stringResource(R.string.none_film),
                        filmAutor =  viewSwapModel.uiState.actualFilm?.autor ?: stringResource(R.string.error_argument),
                        filmMark = if (viewSwapModel.uiState.actualFilm?.mark.toString() == "null") stringResource(R.string.question_mark)
                                        else viewSwapModel.uiState.actualFilm?.mark.toString(),
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
                    .background(SwipeShadowColor)
            )
           }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            IconSwapping(
                imageId = R.drawable.finger_left,
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight(0.8f)
                    .padding(end = 8.dp)
            )
            IconSwapping(
                imageId = R.drawable.finger_right,
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

@Composable
fun IconSwapping(imageId : Int, modifier: Modifier = Modifier) {
    Icon(painter = painterResource(id = imageId),
        contentDescription = null,
        tint = Color.White,
        modifier = modifier
    )
}


