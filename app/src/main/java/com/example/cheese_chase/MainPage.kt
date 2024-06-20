package com.example.cheese_chase

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun MainPage(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xffdcc75e))
    ) {
        val family = FontFamily(
            Font(R.font.caveat)
        )
        var rotate by remember{ mutableStateOf(false)}
        val deg by animateIntAsState(
            targetValue = if (rotate) 180 else 0, label = ""
        )

        Text(
            modifier = Modifier
                .offset(y = 100.dp)
                .align(Alignment.TopCenter),
            text = "CHEESE",
            fontSize = 50.sp,
            fontFamily = family

        )
        Text(
            modifier = Modifier
                .offset(y = 150.dp)
                .align(Alignment.TopCenter),
            fontSize = 50.sp,
            fontFamily = family,
            text = "CHASE"
        )
        Image(
            modifier = Modifier
                .size(50.dp)
                .offset(x = (-20).dp, y = 50.dp)
                .align(Alignment.TopEnd)
                .clickable(
                    onClick = {
                        rotate = !rotate
                        navController.navigate(Screen.Settings.route)
                    }
                )
                .rotate(deg.toFloat()),
            painter = painterResource(R.drawable.settings),
            contentDescription = "settings"
        )
        Image(
            modifier = Modifier
                .size(70.dp)
                .offset(y = (-180).dp)
                .align(Alignment.BottomCenter),
            painter = painterResource(id = R.drawable.lantern),
            contentDescription = "lantern"
        )
        Image(
            modifier = Modifier
                .size(70.dp)
                .offset(y = (-24).dp, x = 70.dp)
                .align(Alignment.BottomStart),
            painter = painterResource(id = R.drawable.plant),
            contentDescription = "plant"
        )
        Image(
            modifier = Modifier
                .size(70.dp)
                .offset(y = (-24).dp, x = (-70).dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.plant),
            contentDescription = "plant"
        )
        Box(
            modifier = Modifier
                .offset(y = (-100).dp, x = 20.dp)
                .align(Alignment.BottomStart)
        ){
            Image(
                modifier = Modifier
                    .size(130.dp),
                painter = painterResource(id = R.drawable.open),
                contentDescription = "signboard"
            )
            Text(
                modifier = Modifier
                    .offset(y = 10.dp)
                    .align(Alignment.Center)
                    .background(Color.Black),
                text = "PLAY",
                fontSize = 38.sp,
                color = Color(0xffdcc75e),
                fontFamily = family
            )
        }
        Image(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(150.dp)
                .align(Alignment.BottomCenter)
                .clickable { navController.navigate(Screen.GamePage.route) },
            painter = painterResource(id = R.drawable.home),
            contentDescription = "jerry's door"
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)

        ){
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .offset(x = (-80).dp, y = 45.dp)
                    .align(Alignment.Center)
                    .clickable {
                        pageFlag.value = 3
                    },
                painter = painterResource(id = R.drawable.podium),
                contentDescription = "High Score"
            )
        }
    }
}