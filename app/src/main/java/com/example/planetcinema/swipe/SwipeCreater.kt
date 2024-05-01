package com.example.planetcinema.swipe

import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.planetcinema.R
import me.saket.swipe.SwipeAction

fun CreateSwipeAction(OnSwipe : () -> Unit, background : Color) : SwipeAction {
    return  SwipeAction(
        onSwipe = OnSwipe,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.empty),
                contentDescription = null,
            )
        },
        background = background
    )
}