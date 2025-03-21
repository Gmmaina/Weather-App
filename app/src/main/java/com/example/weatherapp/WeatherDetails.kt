package com.example.weatherapp

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.api.WeatherModel

@Composable
fun WeatherDetails(data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "${data.location.name},",
                fontSize = 30.sp
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = data.location.country,
                fontSize = 18.sp,
                color = Color.LightGray
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = "${data.current.temp_c}° C",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Condition Icon"
        )


        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                WeatherKeyVal("Humidity", data.current.humidity)
                WeatherKeyVal("Heat Index", "${data.current.heatindex_c}° C")
                WeatherKeyVal("UV", data.current.uv.toString())
                WeatherKeyVal("Pressure", "${data.current.pressure_mb} mb")
                WeatherKeyVal("Wind Direction", data.current.wind_dir)
                WeatherKeyVal("Wind Speed", "${data.current.wind_kph} kph")
                WeatherKeyVal("Local Time", data.location.localtime.split(" ")[1])
                WeatherKeyVal("Local Date", data.location.localtime.split(" ")[0])
            }
        }

    }
}

@Composable
fun WeatherKeyVal(key: String, value: String) {
    Row(
        Modifier.padding(16.dp),
    ) {
        Text(key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(Modifier.weight(1f))
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}