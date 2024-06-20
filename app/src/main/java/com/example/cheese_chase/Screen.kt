package com.example.cheese_chase

sealed class Screen(val route: String) {
    data object MainPage : Screen("main_page")
    data object GamePage : Screen("game_page")
    data object Settings : Screen("settings")
}