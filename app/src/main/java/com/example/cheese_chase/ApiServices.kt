package com.example.cheese_chase

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit
    .Builder()
    .baseUrl("https://chasedeux.vercel.app")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val gameService = retrofit.create(ApiServices::class.java)
interface ApiServices{
    @GET("/obstacleLimit")
    suspend fun getObstacleLimit(): ObstacleLimit

    @GET("/image")
    suspend fun getImage(@Query("character") character: String): ImageResponse
}