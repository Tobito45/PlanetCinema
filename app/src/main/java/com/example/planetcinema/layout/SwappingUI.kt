package com.example.planetcinema.layout

import android.content.res.Configuration
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.planetcinema.R


@Composable
fun SwappingCard(orientation : Int, filmName : String, filmAutor : String, filmMark : String) {
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        SwappingCardPortrait(filmName, filmAutor, filmMark)
    } else {
        SwappingCardLandScape(filmName, filmAutor, filmMark)
    }
}

@Composable
private fun SwappingCardLandScape(filmName : String, filmAutor : String,  filmMark : String) {
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
                    Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth(1f)
                    .padding(start = 15.dp)) {
                    TextInfoFilm(filmName = filmName,
                                filmAutor = filmAutor,
                                filmMark = filmMark,
                                sizeMainText = 25,
                                sizeSmallText = 20,
                        smallTextModifier = Modifier.padding(top = 10.dp),
                        smallRowModifier = Modifier
                            .fillMaxSize(0.7f)
                            .padding(top = 10.dp))
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
private fun SwappingCardPortrait(filmName : String, filmAutor : String, filmMark : String) {
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
                TextInfoFilm(filmName = filmName,
                    filmAutor = filmAutor,
                    filmMark = filmMark,
                    sizeMainText = 20,
                    sizeSmallText = 15,

                    smallRowModifier = Modifier
                        .fillMaxSize(),

                    )
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

