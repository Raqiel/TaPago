package com.example.tapago.ui.components


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.tapago.ui.theme.MediumColor
import com.example.tapago.ui.theme.NewYellow
import com.example.tapago.ui.theme.Oswald
import com.example.tapago.ui.theme.RobotoRegular
import java.util.Locale

@Composable
fun FeaturedText(
    text: String,
    size: Int? = null,
    color: Color = MediumColor
) {
    Text(
        text = text.uppercase(Locale.ROOT),
        color = MediumColor,
        fontSize = size?.sp ?: 40.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 60.sp,
        fontFamily = Oswald
    )
}

@Composable
fun MediumText(
    text: String,
    size: Int? = null,
    color: Color = NewYellow
) {
    Text(
        text = text,
        color = MediumColor,
        fontSize = size?.sp ?: 20.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 60.sp,
        fontFamily = Oswald
    )
}

@Composable
fun SmallText(
    text: String,
    size: Int? = null,
    color: Color = Color(0xFFE5E5E5)
) {
    Text(
        text = text,
        color = color,
        fontSize = size?.sp ?: 12.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = RobotoRegular
    )
}

