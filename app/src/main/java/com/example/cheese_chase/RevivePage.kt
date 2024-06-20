package com.example.cheese_chase

import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun RevivePage(){
    var value by remember{ mutableStateOf(0) }
    var rotate by remember{ mutableStateOf(0) }
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    val revive = MediaPlayer.create(context, R.raw.revive)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.5f))
            .clickable {  }
    ){
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(440.dp),
            painter = painterResource(id = R.drawable.cheesebackground),
            contentDescription = "cheese background"
        )
        Image(
            modifier = Modifier
                .offset(y = -(90).dp)
                .align(Alignment.Center)
                .size(180.dp),
            painter = painterResource(id = R.drawable.angel),
            contentDescription = "angel"
        )
        Text(
            modifier = Modifier
                .offset(y = 40.dp)
                .align(Alignment.Center),
            text = "Revive?",
            fontSize = 50.sp,
            fontStyle = FontStyle.Italic,
        )
        Box(
            modifier = Modifier
                .offset(y = 120.dp)
                .align(Alignment.Center)
        ){
            Button(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp),
                onClick = {
                    if (preferencesManager.getInteger("cheese", 0) > 0){
                        revive.start()
                        preferencesManager.saveInteger("cheese", preferencesManager.getInteger("cheese", 0) - 1)
                        revived.value = true
                        pageFlag.value= 0
                    }
                    else{
                        Toast.makeText(context, "Not enough cheese", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {}
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp),
                painter = painterResource(id = R.drawable.cheese),
                contentDescription = "cheese"
            )
            Canvas(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(90.dp)
            ) {
                drawArc(
                    color = Color.White,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    size = size,
                    style = Stroke(width = 20f)
                )
                drawArc(
                    color = Color.Black,
                    startAngle = 0f,
                    sweepAngle = value.toFloat(),
                    useCenter = false,
                    size = size,
                    style = Stroke(width = 20f)
                )
            }
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .size(80.dp)
                    .align(Alignment.Center)
                    .background(Color.White.copy(alpha = 0.5f)),
                text = if (rotate < 5) "${5-rotate}" else "0",
                textAlign = TextAlign.Center,
                fontSize = 50.sp
            )
        }
    }

    LaunchedEffect(key1 = rotate){
        if (rotate < 5){
            value = 0
            for(i in 0..360){
                delay(1)
                value += 1
            }
            rotate += 1
        }
        else{
            pageFlag.value = 1
        }
    }
}