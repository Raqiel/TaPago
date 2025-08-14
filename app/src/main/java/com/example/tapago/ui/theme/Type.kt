package com.example.tapago.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.tapago.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val PlayFair = FontFamily(
    Font(R.font.playfair, FontWeight.Normal)
)

val Oswald = FontFamily(
    Font(R.font.oswaldbold, FontWeight.ExtraBold)
)

val RobotoThin = FontFamily(
    Font(R.font.robotothin)
)

val RobotoRegular = FontFamily(
    Font(R.font.robotoregular)
)