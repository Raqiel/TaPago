package com.example.tapago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.LightColor
import com.example.tapago.ui.theme.MediumColor
import com.example.tapago.ui.theme.NormalColor
import com.example.tapago.ui.theme.PlayFair
import java.util.Locale

@Composable
fun WorkoutCardView(
    backgroundColor: androidx.compose.ui.graphics.Color = BackgroundCardsColor,
    titleColor: androidx.compose.ui.graphics.Color = NormalColor,
    descriptionColor: androidx.compose.ui.graphics.Color = LightColor,
    circleShapeColor: androidx.compose.ui.graphics.Color = MediumColor,
    titleText: String,
    descriptionText: String?,
    letterText: String,
    onDelete: () -> Unit,
    onCardClick: () -> Unit,
    onLongClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(backgroundColor)
            .padding(20.dp)
            .clickable { onCardClick() },
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .background(color = circleShapeColor, shape = CircleShape)
                .size(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = letterText,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = PlayFair,
                fontWeight = FontWeight.ExtraBold,
                color = BackgroundCardsColor
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                text = titleText.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                color = titleColor,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontFamily = PlayFair
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (descriptionText != null) {
                Text(
                    text = descriptionText.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    },
                    color = descriptionColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Light
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.End
        ) {
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Top) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "icone menu",
                    tint = FeaturedColor,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onDelete()
                        }
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Preview
@Composable
fun WorkoutCardViewPreview() {
    WorkoutCardView(
        titleText = "Superiores",
        letterText = "A",
        descriptionText = "Esta é a descrição de superiores",
        onDelete = {},
        onCardClick = {},
        onLongClick = {}
    )
}
