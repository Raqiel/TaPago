package com.example.tapago.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tapago.ui.theme.BackgroundCardsColor
import com.example.tapago.ui.theme.FeaturedColor


@Composable
fun AppFloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier
            .padding(32.dp),
        onClick = {
            onClick()
        },
        containerColor = FeaturedColor,
        contentColor = BackgroundCardsColor
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "Adicionar"
        )
    }
}