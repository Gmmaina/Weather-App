package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.api.WeatherModel
import com.example.weatherapp.constants.Constants
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResponse = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResponse: LiveData<NetworkResponse<WeatherModel>> = _weatherResponse

    fun getData(city: String) {
        _weatherResponse.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constants.APIKEY, city)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResponse.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResponse.value = NetworkResponse.Error("Failed to fetch data.")
                }
            } catch (e: Exception) {
                _weatherResponse.value = NetworkResponse.Error("Error")
            }
        }
    }
}