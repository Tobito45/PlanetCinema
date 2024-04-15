package com.example.planetcinema

import android.content.res.Configuration
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
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
    val orientation = LocalConfiguration.current.orientation

    //TOP
    RotatedSquareBackground(orientation);

    //TopText
    Header();

    //MIDLE
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        SwappingCard()
    } else {
        SwappingCardLandScape()
    }

    NavBar(orientation)



}

@Preview(showBackground = true, name = "5-inch Device Landscape", widthDp = 640, heightDp = 360)
@Composable
fun GreetingPreview() {
    PlanetCinemaTheme (darkTheme = true) {
        Greeting()
    }
}

@Composable
fun RotatedSquareBackground(orientation : Int) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFF1B1C1F))) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8f
            val squareSize = size.minDimension * 1.5f - strokeWidth * 2
            val startX = if (orientation == Configuration.ORIENTATION_PORTRAIT)
                (size.width - squareSize) / 2 - size.height / 2.5f
                else (size.width - squareSize)/2 - size.width/1.9f

            val startY = if (orientation == Configuration.ORIENTATION_PORTRAIT)
                (size.height - squareSize) / 2 - size.height / 2.5f
                else size.height/4.5f

            val rotating = if (orientation == Configuration.ORIENTATION_PORTRAIT) 45f else 75f


            rotate(rotating) {//45f
                    drawRoundRect(
                        color = Color(0xFFBF3641),
                        topLeft = Offset(startX, startY),
                        size = Size(squareSize, squareSize),
                        cornerRadius = CornerRadius(24.dp.toPx())
                    )
            }
        }
    }
}

@Composable
fun SwappingCardLandScape() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize(),
    ) {
        Icon(painter = painterResource(id = R.drawable.finger_left),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.1f)
                .fillMaxHeight()
                .padding(8.dp)
        )

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
                Image(
                    painter = painterResource(id = R.drawable.the_hunger_games_movie_poster),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(8.dp))

                Column (
                    verticalArrangement = Arrangement.Center,
                    modifier =
                            Modifier.fillMaxHeight(0.8f)
                                .fillMaxWidth(1f)
                                .padding(start = 15.dp)
                ) {
                    Column {
                        Text(
                            text = "The Hunger Games",
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontSize = 25.sp
                        )

                        val annotatedString = buildAnnotatedString {
                            append("By ")
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                append("Gary Ross")
                            }
                        }
                        Text(
                            text = annotatedString,
                            color = Color.White,
                            fontWeight = FontWeight.Light,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                    Row (
                        modifier = Modifier.
                        fillMaxSize(0.7f)
                            .padding(top = 10.dp)
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC700),
                            modifier = Modifier.padding(end = 8.dp)
                                .size(30.dp)
                        )
                        Text(
                            text = "7.8",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "/10",
                            color = Color.Gray,
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .align(Alignment.Bottom)
                        )
                    }

                }
            }
            Box( modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(Color(0xFF2F3030))
                .align(Alignment.CenterHorizontally)
            ) {

            }
        }

        Icon(painter = painterResource(id = R.drawable.finger_right),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
        )
    }
}

@Composable
fun SwappingCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp, top = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.the_hunger_games_movie_poster),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        )
        Box (
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.5f)
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
                    val annotatedString = buildAnnotatedString {
                        append("By ")
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append("Gary Ross")
                        }
                    }
                    Text(
                        text = annotatedString,
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
                        modifier = Modifier
                            .padding(top = 8.dp, end = 8.dp)
                            .align(Alignment.Top)
                    )
                }
            }
        }
        Box (
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .fillMaxHeight(0.2f)
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

}

@Composable
fun Header() {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(
                horizontal = 50.dp,
                vertical = 25.dp
            )
            .fillMaxWidth(),
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
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .size(50.dp),
        )
    }
}

@Composable
fun NavBar(orientation : Int) { //mb to view model
    //NAVBAR
    val navbarSize = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1f else 0.6f
    val navbarClip = if (orientation == Configuration.ORIENTATION_PORTRAIT) 0.dp else 20.dp
    var selectedItem by remember { mutableIntStateOf(0) } // go to view
    val items = listOf(
        R.drawable.lottery,
        R.drawable.swipe,
        R.drawable.list
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NavigationBar(containerColor = Color(0xFF3E3F3F),
                        modifier = Modifier
                            .fillMaxWidth(navbarSize)
                            .clip(RoundedCornerShape(topStart = navbarClip, topEnd = navbarClip))) {
            items.forEachIndexed { index, resourceId ->
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = resourceId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(0.5f),
                        tint = Color.White) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index}
                )
            }
        }
    }
}