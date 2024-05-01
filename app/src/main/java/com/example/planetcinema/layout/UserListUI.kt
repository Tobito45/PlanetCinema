package com.example.planetcinema.layout

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
fun UserListCard(orientation: Int) {
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        UserListScrollPortrait()
    } else {
        UserListScrollLandscape()
    }
}

@Composable
private fun UserListScrollPortrait() {
    Column {
        Spacer(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
        ) {
            items(100) { index ->
                UserListElement(
                    filmName = "The Hunger Games",
                    filmAutor = "Gary Ross",
                    filmMark = "7.8",
                    Modifier
                        .fillMaxWidth(0.9f)
                        .height(170.dp)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF3E3F3F)),
                    imageModifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(20.dp),
                    smallTextModifier = Modifier.padding(top = 10.dp),
                    markRowModifier = Modifier
                        .fillMaxSize(0.7f)
                        .padding(top = 18.dp)
                )
            }

        }
    }
}


@Composable
private fun UserListScrollLandscape() {
    Column {
        Spacer(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .padding(horizontal = 20.dp)
        ) {
            items(100) { index ->
                Row {
                    UserListElement(
                        filmName = "The Hunger Games",
                        filmAutor = "Gary Ross",
                        filmMark = "7.8",
                        Modifier
                            .fillMaxWidth(0.5f)
                            .height(100.dp)
                            .padding(top = 20.dp, end = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF3E3F3F)),
                        imageModifier = Modifier
                            .fillMaxWidth(0.3f)
                            .padding(10.dp),
                        smallTextModifier = Modifier.padding(top = 2.dp),
                        markRowModifier = Modifier
                            .fillMaxSize(0.85f)
                            .padding(top = 2.dp)
                    )

                    UserListElement(
                        filmName = "The Hunger Games",
                        filmAutor = "Gary Ross",
                        filmMark = "7.8",
                        generalModifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(top = 20.dp, start = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF3E3F3F)),
                        imageModifier = Modifier
                            .fillMaxWidth(0.3f)
                            .padding(10.dp),
                        smallTextModifier = Modifier.padding(top = 2.dp),
                        markRowModifier = Modifier
                            .fillMaxSize(0.85f)
                            .padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun UserListElement(filmName : String,
                    filmAutor : String,
                    filmMark : String,
                    generalModifier : Modifier,
                    imageModifier : Modifier,
                    smallTextModifier : Modifier,
                    markRowModifier: Modifier
) {
    Row(
        modifier = generalModifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.the_hunger_games_movie_poster),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = imageModifier
        )

        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)) {
            TextInfoFilm(
                filmName = filmName,
                filmAutor = filmAutor,
                filmMark = filmMark,
                sizeMainText = 18,
                sizeSmallText = 15,
                smallTextModifier = smallTextModifier,
                smallRowModifier = markRowModifier)
        }


        Checkbox(
            checked = false,
            onCheckedChange = { },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFF1F8E9)
            ),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 20.dp)
        )
    }

}
