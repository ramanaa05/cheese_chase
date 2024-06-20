package com.example.cheese_chase

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HighScorePage(){
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { pageFlag.value = 0 }
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .height(370.dp)
                .width(350.dp)
                .align(Alignment.Center)
                .background(Color(0xff7738C7))
        ){
            Text(
                text = "HIGH SCORE",
                modifier = Modifier
                    .offset(y = 20.dp)
                    .align(Alignment.TopCenter),
                fontSize = 40.sp,
                color = Color.White
            )
            Image(
                modifier = Modifier
                    .offset(y = (-20).dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.trophy),
                contentDescription = "trophy"
            )
            Text(
                text = "${preferencesManager.getInteger("HighScore", 0)}",
                modifier = Modifier
                    .offset(y = 100.dp)
                    .align(Alignment.Center),
                fontSize = 40.sp,
                color = Color.White
            )
        }
    }
}