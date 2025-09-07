package com.example.tapago.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.LightColor
import com.example.tapago.ui.theme.PlayFair


@Composable
fun ImageButton(
    color: Color,
    painter: ImageVector,
    size: Dp? = null
) {
    Column(
        modifier = Modifier
            .size(size ?: 200.dp)
            .background(LightColor, RoundedCornerShape(60.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = painter,
            contentDescription = "",
            modifier = Modifier.size(60.dp),
            tint = BackgroundColor
        )
    }
}


@Composable
fun RoundButton(
    color: Color,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color, CircleShape)
            .size(100.dp)
            .clickable {
                onClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            modifier = Modifier.size(45.dp),
            tint = BackgroundColor
        )
    }
}


@Composable
fun MarombaButton(
    textButton: String?,
    iconButton: ImageVector?,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .background(FeaturedColor),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (textButton != null) {
            Text(
                text = textButton,
                fontFamily = PlayFair,
                color = LightColor,
                fontSize = 18.sp,
            )
        }
        if (iconButton != null) {
            Icon(
                imageVector = iconButton,
                contentDescription = "",
                modifier = Modifier.size(60.dp),
                tint = BackgroundColor
            )
        }
    }
}


@Composable
fun MarombaSecondButton(
    textButton: String?,
    iconButton: ImageVector?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .background(BackgroundColor)
            .border(
                BorderStroke(width = 4.dp, color = FeaturedColor),
                shape = RectangleShape
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (textButton != null) {
            Text(
                text = textButton,
                fontFamily = PlayFair,
                color = LightColor,
                fontSize = 18.sp,
            )
        }
        if (iconButton != null) {
            Icon(
                imageVector = iconButton,
                contentDescription = "",
                modifier = Modifier.size(60.dp),
                tint = FeaturedColor
            )
        }
    }
}




