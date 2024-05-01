package com.example.planetcinema.layout

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.planetcinema.R
import com.example.planetcinema.swipe.CreateSwipeAction
import com.example.planetcinema.view.SwapViewModel
import me.saket.swipe.SwipeableActionsBox


@Composable
fun SwappingCard(orientation : Int, filmName : String,
                 filmAutor : String,
                 filmMark : String,
                 viewModel: SwapViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        SwappingCardPortrait(uiState.actualFilm.name,
                             uiState.actualFilm.autor,
                             uiState.actualFilm.mark.toString(),
                             uiState.actualFilm.url,
                             { viewModel.generateNewFilm() },
                             { viewModel.generateNewFilm() }
        )
    } else {
        SwappingCardLandScape(uiState.actualFilm.name,
                              uiState.actualFilm.autor,
                              uiState.actualFilm.mark.toString(),
                              uiState.actualFilm.url,
                              { viewModel.generateNewFilm() },
                              { viewModel.generateNewFilm() }
        )
    }
}

@Composable
private fun SwappingCardLandScape(filmName : String,
                                  filmAutor : String,
                                  filmMark : String,
                                  filmUrl : String,
                                  OnSwapRight : () -> Unit,
                                  OnSwapLeft : () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize(),
    ) {
        val mLocalContext = LocalContext.current

        val left = CreateSwipeAction(
            OnSwipe = {
                OnSwapLeft()
                Toast.makeText(mLocalContext, "Left", Toast.LENGTH_SHORT).show()
            },
            background = Color(0xFF006400)
        )
        val right = CreateSwipeAction(
            OnSwipe = {
                OnSwapRight()
                Toast.makeText(mLocalContext, "Right", Toast.LENGTH_SHORT).show()
            },
            background = Color(0xFF640000)
        )

        Icon(
            painter = painterResource(id = R.drawable.finger_left),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.1f)
                .fillMaxHeight()
                .padding(8.dp)
        )
        SwipeableActionsBox(
            swipeThreshold = 100.dp,
            startActions = listOf(left),
            endActions = listOf(right),
            backgroundUntilSwipeThreshold = Color.Transparent,
        ) {
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
                    AsyncImage(
                        model = filmUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(0.9f)
                            .padding(8.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier =
                        Modifier
                            .fillMaxHeight(0.8f)
                            .fillMaxWidth(1f)
                            .padding(start = 15.dp)
                    ) {
                        TextInfoFilm(
                            filmName = filmName,
                            filmAutor = filmAutor,
                            filmMark = filmMark,
                            sizeMainText = 25,
                            sizeSmallText = 20,
                            smallTextModifier = Modifier.padding(top = 10.dp),
                            smallRowModifier = Modifier
                                .fillMaxSize(0.7f)
                                .padding(top = 10.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                        .background(Color(0xFF2F3030))
                        .align(Alignment.CenterHorizontally)
                ) {

                }
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.finger_right),
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
private fun SwappingCardPortrait(filmName : String,
                                 filmAutor : String,
                                 filmMark : String,
                                 filmUrl : String,
                                 OnSwapRight : () -> Unit,
                                 OnSwapLeft : () -> Unit)
{
    val mLocalContext = LocalContext.current

    val left = CreateSwipeAction(
        OnSwipe = {
            OnSwapLeft()
            Toast.makeText(mLocalContext, "Left", Toast.LENGTH_SHORT).show()
                  },
        background = Color(0xFF006400)
    )
    val right = CreateSwipeAction(
        OnSwipe = {
            OnSwapRight()
            Toast.makeText(mLocalContext, "Right", Toast.LENGTH_SHORT).show()
                  },
        background = Color(0xFF640000)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp, top = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SwipeableActionsBox(
            swipeThreshold = 100.dp,
            startActions = listOf(left),
            endActions = listOf(right),
            backgroundUntilSwipeThreshold = Color.Transparent,
        ) {
           Column (horizontalAlignment = Alignment.CenterHorizontally) {
           AsyncImage(
               model = filmUrl,
               contentDescription = null,
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .fillMaxWidth(0.8f)
                   .fillMaxHeight(0.75f)
                   .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))

           )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.5f)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(Color(0xFF3E3F3F))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    TextInfoFilm(
                        filmName = filmName,
                        filmAutor = filmAutor,
                        filmMark = filmMark,
                        sizeMainText = 20,
                        sizeSmallText = 15,
                        smallTextHorizontalArrangement = Arrangement.End,
                        smallRowModifier = Modifier
                            .fillMaxSize(),
                        )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .fillMaxHeight(0.2f)
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(Color(0xFF2F3030))
            )
           }

        }
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

