package com.example.cheese_chase

fun obstacleGenerator(): Pair<Float, Float> {
    var x: Float
    var y = 0f
    var c = (1..3).random()
    if (c == 1) {
        x = 50f
    }
    else if (c == 2){
        x = 410f
    }
    else{
        x = 770f
    }
    return Pair(x, y)
}