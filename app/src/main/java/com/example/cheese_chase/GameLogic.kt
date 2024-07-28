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

fun converter(course: String): List<Int> {
    var returnx: Int = 0
    val returny: Int = 0
    var returnType: Int = -2
    when{
        (course == "L") -> {
            returnx = 50
        }
        (course == "M") -> {
            returnx = 410
        }
        (course == "R") ->{
            returnx = 770
        }
        (course == "B") -> {
            returnx = listOf(50, 410, 770).random()
            returnType = listOf(-3, -1).random()
        }
    }
    return listOf(returnx, returny, returnType)
}