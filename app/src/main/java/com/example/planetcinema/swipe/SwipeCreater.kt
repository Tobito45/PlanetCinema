package com.example.planetcinema.swipe

import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.planetcinema.R
import me.saket.swipe.SwipeAction

fun CreateSwipeAction(OnSwipe : () -> Unit, background : Color) : SwipeAction {
    val backgroundWithAlpha = background.copy(alpha = 0.25f)

    return  SwipeAction(
        onSwipe = OnSwipe,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.empty),
                contentDescription = null,
            )
        },
        isUndo = true,
        weight = 2.0,
        background = backgroundWithAlpha
    )
}