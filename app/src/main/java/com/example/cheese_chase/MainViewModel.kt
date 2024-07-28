package com.example.cheese_chase

import android.graphics.BitmapFactory
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream


class MainViewModel: ViewModel() {
    private val _infoState = mutableStateOf(InfoState())
    val infoState: State<InfoState> = _infoState
    init {
        fetchObstacleLimit()
        fetchTrap()
        fetchObstacleCourse(5)
    }

    private fun fetchObstacleLimit(){
        viewModelScope.launch{
            try{
                val response = gameService.getObstacleLimit()
                _infoState.value = _infoState.value.copy(
                    loading = false,
                    obstacleLimit = response,
                    error = null
                )
            } catch(e: Exception){
                _infoState.value = _infoState.value.copy(
                    loading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }
    fun fetchTrap(){
        viewModelScope.launch{
            try{
                val response = gameService.getTrap()
                _infoState.value = _infoState.value.copy(
                    loading = false,
                    trap = response,
                    error = null
                )
            } catch(e: Exception){
                _infoState.value = _infoState.value.copy(
                    loading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }
    fun fetchObstacleCourse(extent: Int){
        viewModelScope.launch{
            try{
                val response = gameService.getObstacleCourse(ObstacleCourseRequest(extent))
                _infoState.value = _infoState.value.copy(
                    loading = false,
                    obstacleCourse = response,
                    error = null
                )
            } catch(e: Exception){
                _infoState.value = _infoState.value.copy(
                    loading = false,
                    error = "Error: ${e.message}"
                )
            }
        }
    }

    data class InfoState(
        val loading: Boolean = true,
        val obstacleLimit: ObstacleLimit = ObstacleLimit(2),
        val trap: Trap = Trap(1, 2, "Increase speed of run by given amount"),
        val obstacleCourse: ObstacleCourse = ObstacleCourse(listOf("M")),
        val error: String? = null
    )
}