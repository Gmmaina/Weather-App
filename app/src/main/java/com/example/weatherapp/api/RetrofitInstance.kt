package com.example.weatherapp.api

import com.example.weatherapp.constants.Constants.BASEURL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi : WeatherApi = getInstance().create(WeatherApi::class.java)
}