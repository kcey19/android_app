package com.shardul.nprtracker.data
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("latest/USD")
    suspend fun getLatestRates(): Response<CurrencyResponse>
}