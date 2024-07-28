package com.example.cheese_chase

data class ObstacleLimit(
    val obstacleLimit: Int
)

data class Trap(
    val type: Int,
    val amount: Int,
    val description: String
)

data class ObstacleCourseRequest(
    val extent: Int
)

data class ObstacleCourse(
    val obstacleCourse: List<String>
)