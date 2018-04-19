package com.metacube.weatherapp

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_weather.*
import org.json.JSONObject
import java.text.DateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

    }

    override fun onStart() {
        super.onStart()
        updateWeatherData(CityPrefernce(this).city)

    }

    private fun updateWeatherData(city: String) {
        object : Thread() {
            override fun run() {
                val json = FetchData.getJSON(this@WeatherActivity, city)
                if (json == null) {
                    handler.post {
                        Toast.makeText(this@WeatherActivity,
                                "No data found",
                                Toast.LENGTH_LONG).show()
                    }
                } else {
                    handler.post { renderWeather(json) }
                }
            }
        }.start()
    }

    private fun renderWeather(json: JSONObject) {
        try {
            cityField.text = json.getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("sys").getString("country")

            val details = json.getJSONArray("weather").getJSONObject(0)
            val main = json.getJSONObject("main")
            detailsField.text = details.getString("description").toUpperCase(Locale.US) +
                    "\n" + "Humidity: " + main.getString("humidity") + "%" +
                    "\n" + "Pressure: " + main.getString("pressure") + " hPa"

            currentTemperatureField.text = String.format("%.2f", main.getDouble("temp")) + " ℃"

            val df = DateFormat.getDateTimeInstance()
            val updatedOn = df.format(Date(json.getLong("dt") * 1000))
            updatedField.text = "Last update: $updatedOn"

//            setWeatherIcon(details.getInt("id"),
//                    json.getJSONObject("sys").getLong("sunrise") * 1000,
//                    json.getJSONObject("sys").getLong("sunset") * 1000)

        } catch (e: Exception) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data")
        }

    }

     fun showInputDialog(v: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change city")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("Go") { dialog, which -> changeCity(input.text.toString()) }
        builder.show()
    }

    fun changeCity(newCity: String) {
        updateWeatherData(newCity)
    }

}

