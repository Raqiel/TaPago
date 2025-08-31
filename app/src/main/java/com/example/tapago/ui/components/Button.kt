package com.example.tapago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.LightColor


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


