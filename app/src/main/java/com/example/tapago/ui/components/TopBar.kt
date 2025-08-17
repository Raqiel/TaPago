package com.example.tapago.ui.components

import android.graphics.Paint
import android.text.Layout
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.GrayW600
import com.example.tapago.ui.theme.GrayW800
import com.example.tapago.ui.theme.Yellow50

@Preview
@Composable
fun PreviewComponents() {
    Column(Modifier.background(BackgroundCardsColor)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundCardsColor)
                .padding(vertical = 12.dp)
        ) {
            DebossedButton()
            Spacer(Modifier.width(10.dp))
            EmbossedButton()
        }
        DebossedCard()
        Spacer(Modifier.height(10.dp))
        EmbossedCard(
            content = {
                Text(
                    text = "A",
                    color = Yellow50,
                )
            },
        )
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
fun DebossedButton(
) {

    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val offset by animateIntAsState(targetValue = if (isPressed) 2 else 9)
    val blur by animateDpAsState(targetValue = if (isPressed) 2.dp else 4.dp)


    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isPressed = true
                }

                is PressInteraction.Release -> {
                    isPressed = false
                }

                is PressInteraction.Cancel -> {
                    isPressed = false
                }
            }
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(80.dp)
                .offset { IntOffset(0, -offset) }
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(GrayW600.copy(alpha = 0.9f), CircleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { }
        )

        Box(
            Modifier
                .size(80.dp)
                .offset { IntOffset(-offset, -offset) }
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(Color.Black.copy(alpha = .4f), CircleShape)
        )
    }
}

@Composable
fun EmbossedButton(
) {

    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val offset by animateIntAsState(targetValue = if (isPressed) 2 else 9)
    val blur by animateDpAsState(targetValue = if (isPressed) 2.dp else 4.dp)


    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isPressed = true
                }

                is PressInteraction.Release -> {
                    isPressed = false
                }

                is PressInteraction.Cancel -> {
                    isPressed = false
                }
            }
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .size(80.dp)
                .offset { IntOffset(offset, offset) }
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
        )
        Box(
            Modifier
                .size(80.dp)
                .offset { IntOffset(-offset, -offset) }
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(Color.White.copy(alpha = 0.2f), CircleShape)
        )
        Box(
            Modifier
                .size(80.dp)
                .background(GrayW800.copy(alpha = 0.2f), CircleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { }
        )
    }
}


@Composable
fun DebossedCard(
    height: Dp = 200.dp,
    width: Dp = 400.dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {

    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val offset by animateIntAsState(targetValue = if (isPressed) 4 else 18)
    val blur by animateDpAsState(targetValue = if (isPressed) 4.dp else 8.dp)


    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isPressed = true
                }

                is PressInteraction.Release -> {
                    isPressed = false
                }

                is PressInteraction.Cancel -> {
                    isPressed = false
                }
            }
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier
                .height(height)
                .width(width)
                .offset { IntOffset(0, -offset) }
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(GrayW600.copy(alpha = 0.9f), RoundedCornerShape(20.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { }
        )

        Box(
            modifier
                .height(height)
                .width(width)
                .offset { IntOffset(-offset, -offset) }
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(Color.Black.copy(alpha = .4f), RoundedCornerShape(20.dp))
        ) {
            content()
        }
    }
}


@Composable
fun EmbossedCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {

    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val offset by animateIntAsState(targetValue = if (isPressed) 2 else 9)
    val blur by animateDpAsState(targetValue = if (isPressed) 2.dp else 4.dp)


    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isPressed = true
                }

                is PressInteraction.Release -> {
                    isPressed = false
                }

                is PressInteraction.Cancel -> {
                    isPressed = false
                }
            }
        }
    }

    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier
                .size(80.dp)
                .offset { IntOffset(offset, offset) }
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
        )
        Box(
            modifier
                .size(80.dp)
                .offset { IntOffset(-offset, -offset) }
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
        )
        Box(
            modifier
                .size(80.dp)
                .background(GrayW800.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {},
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}


