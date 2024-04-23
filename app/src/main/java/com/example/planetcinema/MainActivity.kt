package com.example.planetcinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planetcinema.ui.theme.PlanetCinemaTheme
import com.lyh.spintest.SpinWheelComponent
import com.lyh.spintest.SpinWheelItem
import com.lyh.spintest.rememberSpinWheelState
import kotlinx.collections.immutable.toPersistentList
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanetCinemaTheme (darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  //  Test2()
                    PlanetCinemaApp()
                   // WheelPanel()
                    // SwapPanel()
                }
            }
        }
    }
}


@Preview(showBackground = true)//, name = "5-inch Device Landscape", widthDp = 640, heightDp = 360)
@Composable
fun GreetingPreview() {
    PlanetCinemaTheme (darkTheme = true) {
        PlanetCinemaApp()
    }
}

@Composable
fun Test2() {
    val colors1 = remember {
        listOf(Color.Red, Color.Red)
    }
    val colors2 = remember {
        listOf(Color.Yellow, Color.Yellow)
    }

    val items = remember {
        List(10) { index ->
            val colors = if (index % 2 == 0) colors1 else colors2

            SpinWheelItem(colors = colors.toPersistentList()) {
                Text(
                    text = "$$index",
                    style = TextStyle(color = Color(0xFF4CAF50), fontSize = 20.sp)
                )
            }
        }.toPersistentList()
    }
    val spinState = rememberSpinWheelState(
        items = items,
        backgroundImage = R.drawable.spin_wheel_background,
        centerImage = R.drawable.spin_wheel_center,
        indicatorImage = R.drawable.spin_wheel_tick,
        onSpinningFinished = null,
    )
    Box(modifier = Modifier.size(300.dp)) {
        SpinWheelComponent(spinState)
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Button(onClick = { spinState.launchInfinite() },
        //    modifier = Modifier.size(100.dp)
        ) {
            Text(text = "Krut")

        }
        Button(onClick = { spinState.stoppingWheel( Random.nextInt(0, items.count()) ) },
        //    modifier = Modifier.size(100.dp)
        ) {
            Text(text = "Stope")
        }
        }
    }
}


fun getDegreeFromSection(items: List<SpinWheelItem>, section: Int): Float {
    val pieDegree = 360f / items.size
    return pieDegree * section.times(-1)
}

fun getDegreeFromSectionWithRandom(items: List<SpinWheelItem>, section: Int): Float {
    val pieDegree = 360f / items.size
    val exactDegree = pieDegree * section.times(-1)

    val pieReduced = pieDegree * 0.9f //to avoid stop near border
    val multiplier = if (Random.nextBoolean()) 1f else -1f //before or after exact degree
    val randomDegrees = Random.nextDouble(0.0, pieReduced / 2.0)
    return exactDegree + (randomDegrees.toFloat() * multiplier)
}