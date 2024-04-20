package com.example.planetcinema.layout

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.planetcinema.R


@Composable
fun NavBar(orientation : Int, selectedIndex : Int = 0) { //mb to view model
    //NAVBAR
    val navbarSize = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1f else 0.6f
    val navbarClip = if (orientation == Configuration.ORIENTATION_PORTRAIT) 0.dp else 20.dp
    var selectedItem by remember { mutableIntStateOf(selectedIndex) } // go to view
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