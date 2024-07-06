package com.example.cheese_chase

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel: ViewModel() {
    private val _infoState = mutableStateOf(InfoState())
    val infoState: State<InfoState> = _infoState
    init {
        fetchObstacleLimit()
        fetchImage("tom")
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
    private fun fetchImage(character: String) {
        viewModelScope.launch {
            try {
                val response = gameService.getImage(character)
                _infoState.value = _infoState.value.copy(
                    imageUrl = response.imageUrl,
                    error = null
                )
            } catch (e: Exception) {
                _infoState.value = _infoState.value.copy(
                    error = "Error: ${e.message}"
                )
            }
        }
    }
    data class InfoState(
        val loading: Boolean = true,
        val obstacleLimit: ObstacleLimit = ObstacleLimit(2),
        val imageUrl: String? = null,
        val error: String? = null
    )
}