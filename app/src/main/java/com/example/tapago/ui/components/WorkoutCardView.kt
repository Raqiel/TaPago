package com.example.tapago.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.Black
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.LightColor
import com.example.tapago.ui.theme.MediumColor
import com.example.tapago.ui.theme.NewYellow
import com.example.tapago.ui.theme.NormalColor
import com.example.tapago.ui.theme.PlayFair
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
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
    hazeState: HazeState? = null,
    onDelete: () -> Unit,
    onCardClick: () -> Unit,
    onLongClick: () -> Unit
) {

    Box() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
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


@Composable
fun WorkoutCardGlass(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    backgroundColor: androidx.compose.ui.graphics.Color = BackgroundCardsColor,
    titleColor: androidx.compose.ui.graphics.Color = NewYellow,
    descriptionColor: androidx.compose.ui.graphics.Color = LightColor,
    circleShapeColor: androidx.compose.ui.graphics.Color = NewYellow,
    titleText: String,
    descriptionText: String?,
    letterText: String,
    hazeState: HazeState? = null,
    onDelete: () -> Unit,
    onCardClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Transparent)
                .border(
                    2.dp,
                    NewYellow,
                    RoundedCornerShape(20.dp),
                )

        ){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(8.dp)
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
                        fontSize = 12.sp,
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
                                }
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun testes() {
    val hazeState = rememberHazeState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .hazeSource(hazeState)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.Red)

        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .height(100.dp)
                .background(Color.Blue)
        )
    }
    WorkoutCardGlass(
        hazeState = hazeState,
        onBackClick = {},
        letterText = "A",
        descriptionText = "Esta é a descrição de superiores",
        onDelete = {},
        onCardClick = {},
        titleText = "teste",
        onLongClick = {}
    )


}
