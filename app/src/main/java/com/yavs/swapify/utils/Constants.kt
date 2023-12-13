package com.yavs.swapify.utils

object Constants {

    object Deezer{
        const val APP_ID: Long = 649821
        const val SECRET: String = "48e934960b566e8d508497542ba01205"
        const val REDIRECT_URL: String = "http://yavs.swapify/deezer"
        const val BASE_URL = "https://api.deezer.com"
        const val AUTH_URL = "https://connect.deezer.com"
    }

    object Spotify{
        const val APP_ID: String = "ae11204407da494882852e37caa3b9de"
        const val APP_CRED: String = "YWUxMTIwNDQwN2RhNDk0ODgyODUyZTM3Y2FhM2I5ZGU6ZDBiOTFjZTc4NDBhNGQ4NWJiNzczNDFmMmVhZTA4OGM="
        const val REDIRECT_URL: String = "http://yavs.swapify/spotify"
        const val BASE_URL = "https://api.spotify.com"
        const val AUTH_URL = "https://accounts.spotify.com"
    }
}