package com.example.planetcinema.layout

import android.content.res.Configuration
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.planetcinema.data.Film
import com.example.planetcinema.view.UserListViewModel
import kotlin.math.ceil


@Composable
fun UserListCard(orientation: Int,
                 viewModel: UserListViewModel = viewModel()
) {
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        UserListScrollPortrait(viewModel.getAllFilms())
    } else {
        UserListScrollLandscape(viewModel.getAllFilms())
    }
}

@Composable
private fun UserListScrollPortrait(films : List<Film>) {
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
            items(films.size) { index ->
                UserListElement(
                    filmName = films[index].name,
                    filmAutor = films[index].autor,
                    filmMark = films[index].mark.toString(),
                    filmUrl = films[index].url,
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
private fun UserListScrollLandscape(films : List<Film>) {
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
            items(ceil(films.size.toFloat() / 2.0f).toInt()) { index ->
               Row {
                    UserListElement(
                        filmName = films[index].name,
                        filmAutor = films[index].autor,
                        filmMark = films[index].mark.toString(),
                        filmUrl = films[index].url,
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
                   val secondIndex = films.size - 1 - index
                    if (secondIndex != index) {
                        UserListElement(
                            filmName = films[secondIndex].name,
                            filmAutor = films[secondIndex].autor,
                            filmMark = films[secondIndex].mark.toString(),
                            filmUrl = films[secondIndex].url,
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
}

@Composable
fun UserListElement(filmName : String,
                    filmAutor : String,
                    filmMark : String,
                    filmUrl : String,
                    generalModifier : Modifier,
                    imageModifier : Modifier,
                    smallTextModifier : Modifier,
                    markRowModifier: Modifier
) {
    Row(
        modifier = generalModifier
    ) {
        AsyncImage(
            model = filmUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = imageModifier
        )
        /*Image(
            painter = painterResource(id = R.drawable.the_hunger_games_movie_poster),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = imageModifier
        )*/

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

