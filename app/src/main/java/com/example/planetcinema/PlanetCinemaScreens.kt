package com.example.planetcinema

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.planetcinema.layout.Header
import com.example.planetcinema.layout.NavBar
import com.example.planetcinema.layout.SquareBackgroundHeader
import com.example.planetcinema.layout.SwappingCard
import com.example.planetcinema.layout.UserListCard
import com.example.planetcinema.layout.WheelCard


@Composable
fun SwapPanel(modifier: Modifier = Modifier) {
    val orientation = LocalConfiguration.current.orientation

    SquareBackgroundHeader(orientation, 0);

    Header("Discover");

    SwappingCard(orientation, "The Hunger Games", "Gary Ross", "7.8")

    NavBar(orientation)
}

@Composable
fun WheelPanel(modifier: Modifier = Modifier) {
    val orientation = LocalConfiguration.current.orientation

    //RotatedSquareBackground(orientation);
    SquareBackgroundHeader(orientation, 1);

    Header("Select a movie");

    WheelCard(orientation)

    NavBar(orientation)
}

@Composable
fun UserListPanel(modifier: Modifier = Modifier) {
    val orientation = LocalConfiguration.current.orientation

    SquareBackgroundHeader(orientation, 2);

    Header("All movies");

    UserListCard(orientation)

    NavBar(orientation)
}