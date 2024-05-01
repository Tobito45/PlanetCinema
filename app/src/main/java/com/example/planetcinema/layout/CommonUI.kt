package com.example.planetcinema.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp

@Composable
fun TextInfoFilm(filmName: String, filmAutor : String, filmMark : String,
                 sizeMainText : Int, sizeSmallText : Int, smallTextModifier: Modifier = Modifier,
                 smallRowModifier : Modifier) {
        Column {
            Text(
                text = filmName,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = sizeMainText.sp
            )

            val annotatedString = buildAnnotatedString {
                append("By ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append(filmAutor)
                }
            }
            Text(
                text = annotatedString,
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = sizeSmallText.sp,
                modifier = smallTextModifier// Modifier.padding(top = 10.dp)
            )
        }
        Row (
            horizontalArrangement = Arrangement.End,
            modifier = smallRowModifier
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFC700),
                modifier = Modifier.padding(end = 8.dp)
                    .size(30.dp)
            )
            Text(
                text = filmMark,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = "/10",
                color = Color.Gray,
                fontWeight = FontWeight.Light,
                fontSize = 15.sp,
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(top = 8.dp)
            // .align(Alignment.Bottom)
            )
       // }

    }
}



@Composable
fun AutoResizedText(
    text: String,
    style: TextStyle = MaterialTheme.typography.body1,
    modifier: Modifier = Modifier,
    color: Color = style.color
) {
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }
    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val defaultFontSize = MaterialTheme.typography.body1.fontSize

    Text(
        text = text,
        color = color,
        modifier = modifier.drawWithContent {
            if (shouldDraw) {
                drawContent()
            }
        },
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (style.fontSize.isUnspecified) {
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = defaultFontSize
                    )
                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            } else {
                shouldDraw = true
            }
        }
    )
}