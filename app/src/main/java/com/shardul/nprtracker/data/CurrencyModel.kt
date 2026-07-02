package com.shardul.nprtracker.data

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("base_code") val baseCode:String,
    @SerializedName("conversion_rates") val rates: Map<String, Double>,
    val result:String
)