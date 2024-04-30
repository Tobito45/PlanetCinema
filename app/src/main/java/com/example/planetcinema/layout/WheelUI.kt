package com.example.planetcinema.layout

import android.content.res.Configuration
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planetcinema.R
import com.example.planetcinema.view.WheelViewModel
import com.lyh.spintest.SpinWheelComponent
import com.lyh.spintest.SpinWheelItem
import com.lyh.spintest.SpinWheelState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@Composable
fun WheelCard(orientation : Int, viewModel: WheelViewModel = viewModel()) {
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        WheelCardPortarait(viewModel)
    } else {
        WheelCardLandScape(viewModel)
    }
}



@Composable
private fun WheelCardPortarait(viewModel : WheelViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()) {
        Spacer(modifier =
        Modifier.fillMaxHeight(0.15f))

        Wheel(
            films = uiState.films,
            generateNumber = { viewModel.getRandomWheelNumber(it) },
            modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
            activeButtons = { viewModel.activeAllButtons(it) },
            orientation = 0,
        )

        Spacer(modifier =
        Modifier.fillMaxHeight(0.25f))
        Row (
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)) {

            WheelButton(
                OnButtonClicked =  { viewModel.addFilm(uiState.films) },
                isEnable = uiState.activeButtons,
                textButton = "Add movie",
                buttonIcon = Icons.Filled.Add
            )
            WheelButton(
                OnButtonClicked =  { viewModel.clearFilms() },
                isEnable = uiState.films.isNotEmpty() && uiState.activeButtons,
                textButton =  "Clear",
                buttonIcon =  Icons.Filled.Delete
            )
        }
    }
}

@Composable
private fun WheelCardLandScape(viewModel: WheelViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center  ,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier =
        Modifier.fillMaxHeight(0.25f))


        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f)) {

            WheelButton(
                OnButtonClicked =  { viewModel.addFilm(uiState.films) },
                isEnable = uiState.activeButtons,
                textButton = "Add movie",
                buttonIcon =  Icons.Filled.Add,
                modifier =  Modifier.padding(20.dp)
            )
            WheelButton(
                OnButtonClicked =  { viewModel.clearFilms() },
                isEnable = uiState.films.isNotEmpty() && uiState.activeButtons,
                textButton = "Clear",
                buttonIcon = Icons.Filled.Delete
            )
        }
        Wheel(
            films = uiState.films,
            generateNumber = { viewModel.getRandomWheelNumber(it) },
            modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.5f).padding(bottom = 100.dp),
            activeButtons = { viewModel.activeAllButtons(it) },
            orientation = 1,
            )
    }

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
            containerColor = Color(0xFF3E3F3F),
            disabledContainerColor = Color(0xFF181818),
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
                color = if(isEnable)  Color.White  else Color(0xFF777777),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Icon(
                imageVector = buttonIcon,
                tint = if(isEnable)  Color.White  else Color(0xFF777777),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Wheel(modifier: Modifier,
          films : List<String>,
          generateNumber : (Int) -> Int,
          activeButtons : (Boolean) -> Unit,
          orientation: Int
) {
    var listColors = listOf(
            listOf(Color(0xFF02315E), Color(0xFF02315E)),
            listOf(Color(0xFF00457E), Color(0xFF00457E)),
            listOf(Color(0xFF2F70AF), Color(0xFF2F70AF)),
            listOf(Color(0xFFB9848C), Color(0xFFB9848C)),
            listOf(Color(0xFF806491), Color(0xFF806491)),
    )

    val items =
        List(if(films.isEmpty()) 1 else films.size) { index ->
            val colors = listColors[ if (index < 5) index % 5
                                    else  (index) % 4  + 1];
            SpinWheelItem(colors = colors.toPersistentList()) {
                AutoResizedText(
                    text = if(films.isEmpty()) "Empty List " else  films[index],
                    style = TextStyle(color = Color.White, fontSize =
                        if(orientation == 0) 18.sp else 10.sp),
                    modifier = Modifier.rotate(90f).padding(start =
                        if(orientation == 0) 80.dp else 40.dp)
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
    )
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier) {
        SpinWheelComponent(spinState)
        Button(
            onClick = {
                coroutineScope.launch {
                    activeButtons(false)
                    spinState.launchInfinite()
                    delay(2000)
                    spinState.stoppingWheel(generateNumber(films.size))
                    delay(8000)
                    activeButtons(true)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
                ),
            enabled = films.isNotEmpty(),
            modifier = Modifier.fillMaxSize().alpha(0f),
        ) {}
    }
}


@Composable
fun AutoResizedText(
    text: String,
    style: TextStyle = MaterialTheme.typography.body1,
    modifier: Modifier = Modifier,
    color: Color = style.color
) {
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }
    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val defaultFontSize = MaterialTheme.typography.body1.fontSize

    Text(
        text = text,
        color = color,
        modifier = modifier.drawWithContent {
            if (shouldDraw) {
                drawContent()
            }
        },
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (style.fontSize.isUnspecified) {
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = defaultFontSize
                    )
                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            } else {
                shouldDraw = true
            }
        }
    )
}