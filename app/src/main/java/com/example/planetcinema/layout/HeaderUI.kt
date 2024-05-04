package com.example.planetcinema.layout

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planetcinema.R

@Composable
fun Header(panelName : String, onIconClick: () -> Unit) {
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
            panelName,
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        IconButton(onClick = onIconClick,
                modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .size(50.dp),) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_tune_36),
                null,
                tint = Color.White,

            )
        }
    }
}


@Composable
fun SquareBackgroundHeader(orientation : Int, menu: Int) {
    if(orientation == Configuration.ORIENTATION_LANDSCAPE
        || menu == 0)
        RotatedSquareBackground(orientation)
    else {
         SquareBackground()
    }
}

@Composable
private fun SquareBackground() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFF1B1C1F))) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8f
            val squareSize = size.minDimension * 1.5f - strokeWidth * 2
            drawRoundRect(
                color = Color(0xFFBF3641),
                topLeft = Offset(0f, 0f),
                size = Size(squareSize, squareSize / 7),
            )
        }
    }
}

@Composable
private fun RotatedSquareBackground(orientation : Int) {
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


            rotate(rotating) {
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