package com.yavs.swapify.api.auth

import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerAuthApi{
    @GET("/oauth/access_token.php")
    suspend fun getToken(
        @Query("app_id") appId: String,
        @Query("secret") secret: String,
        @Query("code") code: String
    ): String
}