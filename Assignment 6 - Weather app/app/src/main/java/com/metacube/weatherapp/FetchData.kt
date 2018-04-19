package com.metacube.weatherapp

import android.content.Context

import org.json.JSONObject

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object FetchData {
    private val OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric"

    fun getJSON(context: Context, city: String): JSONObject? {
        try {
            val url = URL(String.format(OPEN_WEATHER_MAP_API, city))
            val connection = url.openConnection() as HttpURLConnection

            connection.addRequestProperty("x-api-key", "476ff6c7f616b0025cce90099b00ee6f")

            val reader = BufferedReader(
                    InputStreamReader(connection.inputStream))

            val json = StringBuffer(1024)
            var tmp = reader.readLine()

            while ((tmp) != null) {
                json.append(tmp).append("\n")
                tmp = reader.readLine()
            }
            reader.close()

            val data = JSONObject(json.toString())

            // This value will be 404 if the request was not
            // successful
            return if (data.getInt("cod") != 200) {
                null
            } else data

        } catch (e: Exception) {
            return null
        }

    }
}
