package com.example.tapago.ui.screens.splash

import android.annotation.SuppressLint
import android.graphics.ImageDecoder
import android.graphics.Insets.add
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.widget.VideoView
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.tapago.R
import com.example.tapago.ui.theme.Oswald
import com.example.tapago.ui.theme.Yellow700
import java.util.Locale

@Composable
fun SplashScreen() {
    val context = LocalContext.current

//    val uri = Uri.parse("android.resource://${context.packageName}/${R.raw.galinha}")
//    val playerView = remember {
//        PlayerView(context)
//    }
//
//    val playWhenReady = remember {
//        mutableStateOf(true)
//    }
//
//    val player = remember {
//        ExoPlayer.Builder(context).build().apply {
//            this.repeatMode = Player.REPEAT_MODE_ONE
//            this.playWhenReady = playWhenReady.value
//            playerView.player = this
//            prepare()
//            play()
//        }
//    }
//
//    LaunchedEffect(uri) {
//        playerView.useController = false
//        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
//        player.videoScalingMode= C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
//        val mediaItem =  MediaItem.fromUri(uri);
//        player.setMediaItem(mediaItem)
//    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        val imageLoader = ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        Image(
            modifier= Modifier.size(80.dp),
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = R.drawable.frango).apply(block = {
                    size(Size.ORIGINAL)
                }).build(), imageLoader = imageLoader
            ),
            contentDescription = null,
        )

        Text(
            text = "Carregando...",
            color = Yellow700 ,
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

//
//        player.play()

}


