package com.example.planetcinema

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planetcinema.layout.SwappingCard
import com.example.planetcinema.ui.theme.PlanetCinemaTheme
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

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
fun Test1() {

        // Creating a Column Layout
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            // Fetching local context
            val mLocalContext = LocalContext.current

            // Creating a Swipe Action for Calling;
            // setting icon, background and what
            // happens when swiped
            val mCall = SwipeAction(
                icon = painterResource(R.drawable.empty),
                background = Color.Green,
                isUndo = true,
                onSwipe = { Toast.makeText(mLocalContext, "Calling",Toast.LENGTH_SHORT).show()}
            )

            // Creating a Swipe Action for Sending a message;
            // setting icon, background and what happens when swiped
            val mMessage = SwipeAction(
                icon = painterResource(R.drawable.empty),
                background = Color.Yellow,
                isUndo = true,
                onSwipe = { Toast.makeText(mLocalContext, "Sending Message",Toast.LENGTH_SHORT).show()}
            )

            // Creating a Swipe Action Box with start
            // action as calling and end action as sending message
            SwipeableActionsBox(
                startActions = listOf(mCall),
                endActions = listOf(mMessage)) {

                // Creating a Button inside Swipe Action Box
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0XFF0F9D58))
                ) {
                    Text(text = "Swipe Left or Right", fontSize = 25.sp, color = Color.White)
                }
            }
        }
}

@Composable
fun Test2() {
    val orientation = LocalConfiguration.current.orientation
    val archive = SwipeAction(
        onSwipe = {
            Log.d("MainActivity", "Archive")
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.empty),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(16.dp),
            )
        },
        background = Color.Green
    )

    val email = SwipeAction(
        onSwipe = {
            Log.d("MainActivity", "Email")
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.empty),
                contentDescription = null,
                tint = Color.White,

                modifier = Modifier.padding(16.dp),
            )
        },
        background = Color.Blue
    )

    SwipeableActionsBox(
        swipeThreshold = 100.dp,
        startActions = listOf(archive),
        endActions = listOf(email),
    ) {
        SwappingCard(orientation, "The Hunger Games", "Gary Ross", "7.8")
    }
}
