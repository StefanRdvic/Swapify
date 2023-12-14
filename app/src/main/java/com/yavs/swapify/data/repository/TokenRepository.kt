package com.yavs.swapify.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.yavs.swapify.data.model.Token
import com.yavs.swapify.utils.Platform

class TokenRepository(private val sharedPreferences: SharedPreferences, private val gson: Gson){

    fun get(platform: Platform): Token? {
        return gson.fromJson(
            sharedPreferences.getString(platform.name, null),
            Token::class.java
        )
    }

    fun save(platform: Platform, token: Token?) {
        sharedPreferences
            .edit()
            .putString(platform.name, gson.toJson(token))
            .apply()
    }

    fun remove(platform: Platform) {
        sharedPreferences.edit().remove(platform.name).apply()
    }

}