package com.metacube.weatherapp

import android.app.Activity
import android.content.SharedPreferences

class CityPrefernce(activity: Activity) {

    internal var prefs: SharedPreferences

    // If the user has not chosen a city yet, return
    // Sydney as the default city
    internal var city: String
        get() = prefs.getString("city", "Sydney, AU")
        set(city) {
            prefs.edit().putString("city", city).commit()
        }

    init {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE)
    }

}