package com.example.tapago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.BackgroundLight
import com.example.tapago.ui.theme.ErrorColor
import com.example.tapago.ui.theme.NormalColor


@Composable
fun LetterEditText(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .width(80.dp)
            .height(100.dp),
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontSize = 50.sp
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = NormalColor,
            unfocusedTextColor = NormalColor,
            focusedIndicatorColor = BackgroundColor,
            unfocusedIndicatorColor = BackgroundColor
        ),
        placeholder = {
            Text(
                text = "A",
                color = BackgroundLight,
                textAlign = TextAlign.Center,
                fontSize = 50.sp
            )
        }
    )
}

@Composable
fun TextFieldView(
    visualTransformation: VisualTransformation? = null,
    icon: Painter? = null,
    value: String,
    placeholder: String,
    backgroundCardColor: Color = BackgroundCardsColor,
    error: Boolean,
    onValueChange: (String) -> Unit,
    secondIcon: Painter? = null,
    onSecondIconClicked: (() -> Unit) = { }
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                color = BackgroundColor,
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomEnd = 20.dp,
                    bottomStart = 20.dp
                )
            )
            .clickable { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundCardColor),
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {

                if (icon != null) {
                    Icon(
                        painter = icon,
                        contentDescription = "",
                        tint = if (error) ErrorColor else NormalColor
                    )
                }
                TextField(
                    value = value,
                    onValueChange = {
                        //value. = it
                        onValueChange(it)
                    },
                    modifier = Modifier
                        .fillMaxHeight(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = NormalColor,
                        unfocusedTextColor = NormalColor,
                        focusedIndicatorColor = BackgroundColor,
                        unfocusedIndicatorColor = BackgroundColor
                    ),
                    visualTransformation = visualTransformation ?: VisualTransformation.None,
                    placeholder = {

                        Text(
                            text = placeholder,
                            color = BackgroundLight
                        )
                    }
                )
                if (secondIcon != null) {
                    Icon(
                        painter = secondIcon,
                        contentDescription = "",
                        tint = if (error) ErrorColor else NormalColor,
                        modifier = Modifier.clickable {

                            onSecondIconClicked()

                        }
                    )
                }
            }

        }

    }
}


@Composable
fun EditNormalText(
    value: String,
    placeholder: String,
    fontSize: Int?,
    onValueChange: (String) -> Unit
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = NormalColor,
            unfocusedTextColor = NormalColor,
            focusedIndicatorColor = BackgroundColor,
            unfocusedIndicatorColor = BackgroundColor
        ),
        textStyle = TextStyle(
            fontSize = fontSize?.sp ?: 16.sp
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = BackgroundLight,
                textAlign = TextAlign.Center,
                fontSize = fontSize?.sp ?: 16.sp
            )
        }
    )
}


