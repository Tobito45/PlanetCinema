package com.example.planetcinema.layout

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planetcinema.R
import com.example.planetcinema.data.film.Film
import com.example.planetcinema.ui.theme.ButtonBackGroundColor
import com.example.planetcinema.ui.theme.DisableButtonBackGroundColor
import com.example.planetcinema.ui.theme.DisableTextButton
import com.example.planetcinema.ui.theme.WheelColor1
import com.example.planetcinema.ui.theme.WheelColor2
import com.example.planetcinema.ui.theme.WheelColor3
import com.example.planetcinema.ui.theme.WheelColor4
import com.example.planetcinema.ui.theme.WheelColor5
import com.example.planetcinema.view.FilterViewModel
import com.example.planetcinema.view.WheelViewModel
import com.lyh.spintest.SpinWheelComponent
import com.lyh.spintest.SpinWheelItem
import com.lyh.spintest.SpinWheelState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WheelCard(orientation : Int, viewWheelModel: WheelViewModel, viewFilterModel : FilterViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val rangeSliderState = remember {
        RangeSliderState(
            activeRangeStart = viewFilterModel.uiState.filmRange.start,
            activeRangeEnd = viewFilterModel.uiState.filmRange.endInclusive,
            valueRange = viewFilterModel.uiState.filmRange,
        )
    }

    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        WheelCardPortarait(viewWheelModel = viewWheelModel,
                            viewFilterModel = viewFilterModel,
                            rangeSliderState = rangeSliderState,
                            scope = coroutineScope,
                            sheetState = sheetState)
    } else {
        WheelCardLandScape(viewWheelModel = viewWheelModel,
                            viewFilterModel = viewFilterModel,
                            rangeSliderState = rangeSliderState,
                            scope = coroutineScope,
                            sheetState = sheetState)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WheelCardPortarait(
    viewWheelModel: WheelViewModel,
    viewFilterModel: FilterViewModel,
    rangeSliderState: RangeSliderState,
    scope: CoroutineScope,
    sheetState: SheetState
) {
    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()) {
        Spacer(modifier =
        Modifier.fillMaxHeight(0.15f))

        Wheel(
            films = viewWheelModel.uiState.filmsInWheel,
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            activeButtons = {
                scope.launch {
                    viewWheelModel.activeAllButtons(it)
                } },
            generateNumber = { viewWheelModel.getRandomWheelNumber(it) },
            orientation = 0,
            animation = viewWheelModel.uiAnimationState.rotation
        )

        Spacer(modifier =
        Modifier.fillMaxHeight(0.25f))
        Row (
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)) {

            WheelButtonsCreater(
                scope = scope,
                viewWheelModel = viewWheelModel,
                viewFilterModel = viewFilterModel
            )
        }
    }

    PopUpsWheel(
        sheetState = sheetState,
        scope = scope,
        viewWheelModel = viewWheelModel,
        viewFilterModel = viewFilterModel,
        rangeSliderState = rangeSliderState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WheelCardLandScape(
    viewWheelModel: WheelViewModel,
    viewFilterModel: FilterViewModel,
    rangeSliderState: RangeSliderState,
    scope: CoroutineScope,
    sheetState: SheetState
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier =
            Modifier.fillMaxHeight(0.25f)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f)
        ) {

            WheelButtonsCreater(
                scope = scope,
                viewWheelModel = viewWheelModel,
                viewFilterModel = viewFilterModel
            )
        }
        Wheel(
            films = viewWheelModel.uiState.filmsInWheel,
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.5f)
                .padding(bottom = 100.dp),
            activeButtons = {
                scope.launch {
                    viewWheelModel.activeAllButtons(it)
                }
            },
            orientation = 1,
            generateNumber = { viewWheelModel.getRandomWheelNumber(it) },
            animation = viewWheelModel.uiAnimationState.rotation
        )
    }

    PopUpsWheel(
        sheetState = sheetState,
        scope = scope,
        viewWheelModel = viewWheelModel,
        viewFilterModel = viewFilterModel,
        rangeSliderState = rangeSliderState
    )
}


@Composable
private fun WheelButton(OnButtonClicked : () -> Unit,
                        isEnable: Boolean,
                        textButton : String,
                        buttonIcon : ImageVector,
                        modifier: Modifier = Modifier

) {
    Button(
        onClick = OnButtonClicked,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonBackGroundColor,
            disabledContainerColor = DisableButtonBackGroundColor,
            ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        enabled = isEnable,
        modifier = modifier
            .size(width = 150.dp, height = 50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = textButton.uppercase(),
                color = if(isEnable)  Color.White  else DisableTextButton,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Icon(
                imageVector = buttonIcon,
                tint = if(isEnable)  Color.White  else DisableTextButton,
                contentDescription = null
            )
        }
    }
}

@Composable
fun Wheel(modifier: Modifier,
          films : List<Film>,
          generateNumber : (Int) -> Int,
          activeButtons : (Boolean) -> Unit,
          orientation: Int,
          animation: Animatable<Float, AnimationVector1D>
) {
    var listColors = listOf(
            listOf(WheelColor1, WheelColor1),
            listOf(WheelColor2, WheelColor2),
            listOf(WheelColor3, WheelColor3),
            listOf(WheelColor4, WheelColor4),
            listOf(WheelColor5, WheelColor5),
    )

    val items =
        List(if(films.isEmpty()) 1 else films.size) { index ->
            val colors = listColors[ if (index < 5) index % 5
                                    else  (index) % 4  + 1];
            SpinWheelItem(colors = colors.toPersistentList()) {
                AutoResizedText(
                    text = if(films.isEmpty()) stringResource(R.string.empty_list) else films[index].name,
                    style = TextStyle(color = Color.White, fontSize =
                        if(orientation == 0) 16.sp else 10.sp),
                    modifier = Modifier
                        .rotate(90f)
                        .padding(
                            start =
                            if (orientation == 0) 90.dp else 40.dp
                        )
                )
            }
        }.toPersistentList()

    val spinState = SpinWheelState(
        items = items,
        backgroundImage = R.drawable.empty,
        centerImage = R.drawable.empty,
        indicatorImage = R.drawable.spin_wheel_tick,
        onSpinningFinished = null,
        initSpinWheelSection = 0,
        stopDuration = 8.seconds,
        stopNbTurn = 3f,
        rotationPerSecond = 0.8f,
        scope = rememberCoroutineScope(),
        rotation = animation
    )
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier) {
        val mLocalContext = LocalContext.current
        SpinWheelComponent(spinState)
        Button(
            onClick = {
                coroutineScope.launch {
                    activeButtons(false)
                    spinState.launchInfinite()
                    delay(2000)
                    val randomNumber = generateNumber(films.size)
                    spinState.stoppingWheel(randomNumber)
                    delay(8000)
                    activeButtons(true)
                    Toast.makeText(mLocalContext,
                        mLocalContext.getString(R.string.random_film_is) + films[randomNumber].name, Toast.LENGTH_LONG).show()
                }
            },
            enabled = films.isNotEmpty(),
            modifier = Modifier
                .fillMaxSize()
                .alpha(0f),
        ) {}
    }
}

@Composable
fun WheelButtonsCreater(
    scope: CoroutineScope,
    viewWheelModel: WheelViewModel,
    viewFilterModel: FilterViewModel
) {
    WheelButton(
        OnButtonClicked = { scope.launch { viewWheelModel.reloadState(true, viewFilterModel::filmsFilter) }},
        isEnable = viewWheelModel.uiState.activeButtons,
        textButton = stringResource(R.string.add_film),
        buttonIcon = Icons.Filled.Add
    )
    WheelButton(
        OnButtonClicked = { viewWheelModel.clearFilms() },
        isEnable = viewWheelModel.uiState.filmsInWheel.isNotEmpty() && viewWheelModel.uiState.activeButtons,
        textButton =  stringResource(R.string.clear),
        buttonIcon =  Icons.Filled.Delete
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUpsWheel(
    sheetState: SheetState,
    scope: CoroutineScope,
    viewWheelModel: WheelViewModel,
    viewFilterModel: FilterViewModel,
    rangeSliderState: RangeSliderState
) {
    WheelBottomSheet(sheetState = sheetState,
        scope = scope,
        canBeShow = viewWheelModel.uiState.showBottomSheet,
        films = viewWheelModel.uiState.films,
        onDismissRequest = {
            scope.launch {
                viewWheelModel.reloadState(
                    false,
                    viewFilterModel::filmsFilter
                )
            }
        },
        filmAdder = { viewWheelModel.addFilm(it) },
        containFilm = { viewWheelModel.containFilm(it) },
        onCloseButton = {
            scope.launch {
                viewWheelModel.reloadState(false, viewFilterModel::filmsFilter)
            }
        })

    FilterUserListAndWheelSheet(viewModel = viewFilterModel,
        sheetState = sheetState,
        scope = scope,
        rangeSliderState = rangeSliderState,
        onCloseButton = {
            scope.launch {
                viewWheelModel.reloadState(false, viewFilterModel::filmsFilter)
            }
        })
}
