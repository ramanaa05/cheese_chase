package com.example.cheese_chase

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun WinnerPage(){
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.5f))
            .clickable { }
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .height(550.dp)
                .width(350.dp)
                .align(Alignment.Center)
                .background(Color(0xffb0b2f6))
        ){
            Image(
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .fillMaxSize()
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.winner),
                contentDescription = "tom caught jerry"
            )
            Text(
                modifier = Modifier
                    .offset(y = 20.dp)
                    .align(Alignment.TopCenter),
                text = "YOU WIN!",
                fontSize = 40.sp,
                color = Color(0xffe6c59e),
                fontWeight = FontWeight.ExtraBold
            )
            Button(
                modifier = Modifier
                    .offset(y = 150.dp)
                    .align(Alignment.Center),
                onClick = {
                    pageFlag.value = 0
                    reset.value = true
                    if (score.value > preferencesManager.getInteger("HighScore", 0)){
                        preferencesManager.saveInteger("HighScore", score.value)
                    }
                    score.value = 0
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffe6c59e))
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp, start = 35.dp, end = 35.dp),
                    text = "PLAY AGAIN",
                    fontSize = 30.sp
                )
            }
            Button(
                modifier = Modifier
                    .offset(y = 220.dp)
                    .align(Alignment.Center),
                onClick = {
                    pageFlag.value = 0
                    toHome.value = true
                    if (score.value > preferencesManager.getInteger("HighScore", 0)){
                        preferencesManager.saveInteger("HighScore", score.value)
                    }
                    score.value = 0
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffe6c59e))
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp, start = 80.dp, end = 80.dp),
                    text = "HOME",
                    fontSize = 30.sp
                )
            }
        }
    }
}