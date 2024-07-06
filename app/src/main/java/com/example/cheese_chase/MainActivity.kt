package com.example.cheese_chase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import com.example.cheese_chase.ui.theme.Cheese_chaseTheme

val pageFlag = mutableStateOf(0)
val toHome = mutableStateOf(false)
val reset = mutableStateOf(false)
val score = mutableStateOf(0)
val totalDistance = mutableStateOf(5000f)
val mode = mutableStateOf(0)
val revived = mutableStateOf(false)
val gun = mutableStateOf(0)
val globalPowerUps = listOf(
    R.drawable.kennel,
    R.drawable.heart
)
val globalTraps = listOf(
    R.drawable.speedometer,
    R.drawable.fragile,
    R.drawable.shield,
    R.drawable.bomb
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Cheese_chaseTheme {
                Navigation()
                if (pageFlag.value == 1){
                    LoserPage()
                }
                if (pageFlag.value == 2){
                    WinnerPage()
                }
                if (pageFlag.value == 3){
                    HighScorePage()
                }
                if (pageFlag.value == 4){
                    RevivePage()
                }
            }
        }
    }
}