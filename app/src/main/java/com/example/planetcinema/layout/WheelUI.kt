package com.example.planetcinema.layout

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planetcinema.R

@Composable
fun WheelCard(orientation : Int) {
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        WheelCardPortarait()
    } else {
        WheelCardLandScape()
    }
}



@Composable
private fun WheelCardPortarait() {
    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()) {
        Spacer(modifier =
        Modifier.fillMaxHeight(0.15f))
        Image(
            painter = painterResource(id = R.drawable.wheel),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
        )
        Spacer(modifier =
        Modifier.fillMaxHeight(0.25f))
        Row (
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth(0.8f)) {

            WheelButton("Add movie", Icons.Filled.Add)
            WheelButton("Clear", Icons.Filled.Delete)
        }
    }
}

@Composable
private fun WheelCardLandScape() {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center  ,
        modifier = Modifier
            .fillMaxSize()) {
        Spacer(modifier =
        Modifier.fillMaxHeight(0.25f))


        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth(0.4f)) {

            WheelButton("Add movie", Icons.Filled.Add, Modifier.padding(20.dp))
            WheelButton("Clear", Icons.Filled.Delete)
        }
        Spacer(modifier =
        Modifier.fillMaxHeight(0.25f))
        Image(
            painter = painterResource(id = R.drawable.wheel),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
        )
    }

}


@Composable
private fun WheelButton(textButton : String, buttonIcon : ImageVector, modifier: Modifier = Modifier ) {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E3F3F)),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        modifier = modifier
            .size(width = 150.dp, height = 50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = textButton.uppercase(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Icon(
                imageVector = buttonIcon,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}
