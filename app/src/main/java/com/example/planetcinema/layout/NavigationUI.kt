package com.example.planetcinema.layout

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planetcinema.R
import com.example.planetcinema.starters.PlaneCinemaScreen
import com.example.planetcinema.ui.theme.ButtonBackGroundColor

val items = listOf(
    R.drawable.lottery,
    R.drawable.swipe,
    R.drawable.list
)

@Composable
fun NavBar(selectedItem: Int, orientation : Int, navController: NavController) { //mb to view model
    val navbarSize = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1f else 0.6f
    val navbarClip = if (orientation == Configuration.ORIENTATION_PORTRAIT) 0.dp else 20.dp


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NavigationBar(containerColor = ButtonBackGroundColor,
            modifier = Modifier
                .fillMaxWidth(navbarSize)
                .clip(RoundedCornerShape(topStart = navbarClip, topEnd = navbarClip))) {
            items.forEachIndexed { index, resourceId ->
                NavigationBarItem(
                    icon = { Icon(painter = painterResource(id = resourceId),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = Color.White) },
                    selected = selectedItem == index,
                    onClick = {
                        navController.navigate(PlaneCinemaScreen.entries[index].name)
                    }
                )
            }
        }
    }
}