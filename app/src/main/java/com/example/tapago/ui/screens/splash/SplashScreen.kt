package com.example.tapago.ui.screens.splash

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.tapago.Destination
import com.example.tapago.R
import com.example.tapago.ui.theme.BackgroundColor
import com.example.tapago.ui.theme.Oswald
import com.example.tapago.ui.theme.Yellow700
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SplashScreen(
    navigate: (Destination) -> Unit,
) {
    LaunchedEffect(Unit) {
        delay(2000)
        navigate(Destination.Home())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.gif_frango)
                .decoderFactory(ImageDecoderDecoder.Factory())
                .build(),
            contentDescription = "Logo frango",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Carregando...",
            color = Yellow700,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = Oswald
        )
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        {}
    )
}


