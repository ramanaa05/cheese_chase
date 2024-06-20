package com.example.cheese_chase

import android.media.MediaPlayer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Settings(navController: NavController){
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffdcc75e))
    ){
        Canvas(
            modifier = Modifier
                .offset(y = 90.dp, x = (-25).dp)
                .align(Alignment.TopEnd)
                .height(50.dp)
                .width(300.dp)
                .background(Color(0xffdcc75e))
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        if (offset.x in 50f..size.width - 50f) {
                            totalDistance.value = (offset.x / (size.width - 50f)) * 5000
                        }
                    }
                }
        ) {
            drawLine(
                color = Color.Gray,
                start = Offset(0f, center.y),
                end = Offset(size.width - 50f, center.y),
                strokeWidth = 15f
            )
            drawLine(
                color = Color.Black,
                start = Offset(0f, center.y),
                end = Offset((size.width - 50f)*(totalDistance.value/5000), center.y),
                strokeWidth = 15f
            )
            drawPoints(
                points = listOf(Offset((size.width - 50f)*(totalDistance.value/5000), center.y)),
                pointMode = PointMode.Points,
                color = Color.Black,
                strokeWidth = 50f,
                cap = StrokeCap.Round
            )
        }
        if(mode.value == 1){
            Box(
                modifier = Modifier
                    .offset(y = 90.dp, x = (-25).dp)
                    .align(Alignment.TopEnd)
                    .height(50.dp)
                    .width(300.dp)
                    .background(Color(0xffdcc75e).copy(alpha = 0.8f))
                    .clickable { }
            )
        }
        Image(
            modifier = Modifier
                .offset(x = 20.dp, y = 90.dp)
                .size(50.dp)
                .align(Alignment.TopStart)
                .clickable {
                    totalDistance.value = 5000f
                    mode.value = (mode.value + 1) % 2
                },
            painter = painterResource(id = if(mode.value == 0) R.drawable.path else R.drawable.infinite),
            contentDescription = "distance"
        )

        Box(
            modifier = Modifier
                .offset(x = 30.dp, y = (-200).dp)
                .align(Alignment.CenterStart)
        ){
            Image(
                modifier = Modifier
                    .size(150.dp),
                painter = painterResource(id = R.drawable.machine_gun),
                contentDescription = "machine gun"
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(150.dp)
                    .background(Color(0xffdcc75e).copy(alpha = if (gun.value == 0) 0f else 0.7f))
                    .clickable { gun.value = 0 }
            )
        }
        Box(
            modifier = Modifier
                .offset(x = (-40).dp, y = (-200).dp)
                .align(Alignment.CenterEnd)
        ){
            Image(
                modifier = Modifier
                    .size(150.dp),
                painter = painterResource(id = R.drawable.rifle),
                contentDescription = "machine gun"
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(150.dp)
                    .background(Color(0xffdcc75e).copy(alpha = if (gun.value == 1) 0f else 0.7f))
                    .clickable { gun.value = 1 }
            )
        }
    }
}