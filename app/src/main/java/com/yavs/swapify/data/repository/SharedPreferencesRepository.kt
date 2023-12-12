package com.yavs.swapify.data.repository

import android.content.SharedPreferences

class SharedPreferencesRepository(private val sharedPreferences: SharedPreferences){

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun putString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

}