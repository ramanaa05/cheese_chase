package com.example.cheese_chase

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Pictures(){
    Box(modifier = Modifier.background(Color.Transparent)){
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
        ){
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color.Black)
                    .height(138.dp)
                    .width(100.dp)
            ){
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.cheese),
                    contentDescription = "cheese"
                )
            }
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.decoratedframe),
                contentDescription = "decorated frame"
            )
        }

        Box(
            modifier = Modifier
                .offset(x = (-20).dp, y = 30.dp)
                .align(Alignment.CenterEnd)
        ){
            Image(
                modifier = Modifier
                    .size(218.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.plainframe),
                contentDescription = "plain frame"
            )
            Image(
                modifier = Modifier
                    .offset(y = 25.dp, x = (-1).dp)
                    .size(176.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.tom_jerry),
                contentDescription = "tom and jerry"
            )
        }

        Box(
            modifier = Modifier
                .offset(y = 140.dp)
                .align(Alignment.TopCenter)
        ){
            Image(
                modifier = Modifier
                    .size(220.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.woodenframe),
                contentDescription = "wooden frame"
            )
            Image(
                modifier = Modifier
                    .size(153.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.jerry),
                contentDescription = "jerry"
            )
        }
    }
}