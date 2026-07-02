package com.shardul.nprtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shardul.nprtracker.data.CurrencyResponse
import com.shardul.nprtracker.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ForexState{
    object Loading : ForexState()
    data class Success(val data: Map<String, Double>) : ForexState()
    data class Error(val message: String) : ForexState()
}

class ForexViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ForexState>(ForexState.Loading)
    val uiState: StateFlow<ForexState> = _uiState

    init{
        fetchRates()
    }
    fun fetchRates(){
        viewModelScope.launch {
            try{
                val response = RetrofitClient.apiService.getLatestRates()
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = ForexState.Success(response.body()!!.rates)
                } else{
                    _uiState.value = ForexState.Error("Failed to fetch data")
                }
            } catch (e: Exception) {
                _uiState.value = ForexState.Error("No Internet Connection")
            }
        }
    }
}
