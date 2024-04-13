package com.example.planetcinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    //TOP
    Box (
        modifier = Modifier
            .fillMaxSize().background(Color(0xFF1B1C1F)) // for preview
    ) {

        RotatedSquareBackground {
            Row(
                modifier
                    .padding(
                        horizontal = 50.dp,
                        vertical = 50.dp
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Discover",
                    color = Color.White,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_tune_36),
                    null,
                    tint = Color.White,
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .size(50.dp),
                )
            }
        }
    }

    //MIDLE
    Column(
        modifier = Modifier
            .fillMaxSize()
             .padding( bottom = 100.dp, top = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.the_hunger_games_movie_poster),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        )
        Box (
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.4f)
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(Color(0xFF3E3F3F))
        ) {
            Row( modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = "The Hunger Games",
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "By Gary Ross",
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp
                    )
                }
                Row (modifier = Modifier
                    .fillMaxSize(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC700),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "7.8",
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "/10",
                        color = Color.Gray,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp).align(Alignment.Top)
                    )
                }
            }
        }
        Box (
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .fillMaxHeight(0.1f)
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(Color(0xFF2F3030))
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(painter = painterResource(id = R.drawable.finger_left),
                contentDescription = null,
                tint = Color.White,
                modifier = modifier.fillMaxWidth(0.25f).
                    fillMaxHeight(0.8f).padding(start = 8.dp))
            Icon(painter = painterResource(id = R.drawable.finger_right),
                contentDescription = null,
                tint = Color.White,
                modifier = modifier.fillMaxWidth(0.25f)
                    .fillMaxHeight(0.8f).padding(end = 8.dp)
            )
        }
    }



    //NAVBAR
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        R.drawable.lottery,
        R.drawable.swipe,
        R.drawable.list
    )

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        NavigationBar(containerColor = Color(0xFF3E3F3F)) {
            items.forEachIndexed { index, resourceId ->
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = resourceId),
                                    contentDescription = null,
                                    modifier = modifier.fillMaxSize(0.5f),
                                    tint = Color.White) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlanetCinemaTheme {
        Greeting()
    }
}

@Composable
fun RotatedSquareBackground(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8f
            val squareSize = size.minDimension * 1.5f - strokeWidth * 2
            val startX = (size.width - squareSize) / 2 - size.height / 2.5f // - size.height / 2.5f
            val startY = (size.height - squareSize) / 2 - size.height / 2.5f

            rotate(45f) {
                    drawRoundRect(
                        color = Color(0xFFBF3641),
                        topLeft = Offset(startX, startY),
                        size = Size(squareSize, squareSize),
                        cornerRadius = CornerRadius(24.dp.toPx())
                    )
            }
        }
        content()
    }
}