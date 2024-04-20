package com.example.planetcinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.planetcinema.ui.theme.PlanetCinemaTheme

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
                   UserListPanel()
                   // WheelPanel()
                    // SwapPanel()
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "5-inch Device Landscape", widthDp = 640, heightDp = 360)
@Composable
fun GreetingPreview() {
    PlanetCinemaTheme (darkTheme = true) {
        UserListPanel()
    }
}
