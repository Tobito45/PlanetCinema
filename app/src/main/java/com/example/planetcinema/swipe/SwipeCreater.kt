package com.example.planetcinema.swipe

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.planetcinema.R
import me.saket.swipe.SwipeAction

fun CreateSwipeAction(onSwipe : () -> Unit, background : Color) : SwipeAction {
    val backgroundWithAlpha = background.copy(alpha = 0.25f)

    return  SwipeAction(
        onSwipe = onSwipe,
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

fun CreateLeftSwipeAction(onSwipe : () -> Unit, context : Context) : SwipeAction  {
    return CreateSwipeAction(
        onSwipe = {
            onSwipe()
            Toast.makeText(context,
                context.getString(R.string.next_film), Toast.LENGTH_SHORT).show()
        },
        background = Color(0xFF640000)
    )
}

fun CreateRightwipeAction(OnSwipe : () -> Unit, context : Context) : SwipeAction  {
    return CreateSwipeAction(
        onSwipe = {
            OnSwipe()
            Toast.makeText(context,
                context.getString(R.string.film_saved), Toast.LENGTH_SHORT).show()
        },
        background = Color(0xFF006400)
    )
}