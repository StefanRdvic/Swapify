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
        const val APP_ID: String = "a338c974499c4b21a1d151969fa907b4"
        const val APP_CRED: String = "YTMzOGM5NzQ0OTljNGIyMWExZDE1MTk2OWZhOTA3YjQ6YzdjYTE0NWNiNzk4NGJmMmIwMjgwMTZkNmUyODkyMjg=="
        const val REDIRECT_URL: String = "http://yavs.swapify/spotify"
        const val BASE_URL = "https://api.spotify.com"
        const val AUTH_URL = "https://accounts.spotify.com"
    }
}