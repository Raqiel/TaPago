package com.example.tapago.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.example.tapago.ui.theme.AppTheme
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.FeaturedColor
import com.example.tapago.ui.theme.White

@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    hasTopBar: Boolean = false,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    backgroundColor: Color = BackgroundColor,
    contentColor: Color = White,
    onBackClick: () -> Unit,
    dialogs: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            if (hasTopBar) {
                AppTopBar(onBackClicked = { onBackClick() })
            }
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = BackgroundColor,
        contentColor = FeaturedColor,
        content = { paddingValues ->
            val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

            val correctedPaddingValues = PaddingValues(
                top = paddingValues.calculateTopPadding() - statusBarHeight,
                bottom = 0.dp,
                start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                end = paddingValues.calculateEndPadding(LocalLayoutDirection.current)
            )

            Box(modifier = Modifier.padding(correctedPaddingValues)) {
                content()
            }
            dialogs()
        }
    )
}