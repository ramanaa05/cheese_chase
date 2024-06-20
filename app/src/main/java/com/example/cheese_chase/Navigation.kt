package com.example.cheese_chase

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainPage.route){
        composable(
            route = Screen.MainPage.route
        ){
            MainPage(navController)
        }
        composable(
            route = Screen.GamePage.route
        ){
            GamePage(navController)
        }
        composable(
            route = Screen.Settings.route
        ){
            Settings(navController)
        }
    }
}