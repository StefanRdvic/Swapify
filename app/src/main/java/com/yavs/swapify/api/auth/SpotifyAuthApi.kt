package com.yavs.swapify.api.auth

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface SpotifyAuthApi{
    @Headers("content-type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/api/token")
    suspend fun getToken(
        @Header("Authorization") auth: String,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUrl: String,
        @Field("grant_type") grantType: String,
    ): String
}