package com.shardul.nprtracker.data

import retrofit2.Response

class CurrencyRepository(private val apiService: CurrencyApiService) {
    suspend fun getLatestRates(): Response<CurrencyResponse> {
        return apiService.getLatestRates()
    }
}