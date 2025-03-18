package com.example.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherPage(weatherViewModel: WeatherViewModel, modifier: Modifier) {
    var city by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val weatherResult = weatherViewModel.weatherResponse.observeAsState()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = city,
                onValueChange = {
                    city = it
                },
                label = {
                    Text(
                        text = "Enter Location..."
                    )
                },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color.LightGray
                )
            )

            IconButton(onClick = {
                weatherViewModel.getData(city)
                keyboardController?.hide()
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }

        when(val result = weatherResult.value){
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data)
            }
            is NetworkResponse.Error -> {
                Text(
                    text = result.message
                )
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            null -> {}
        }
    }
}