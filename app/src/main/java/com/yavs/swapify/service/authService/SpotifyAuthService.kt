package com.yavs.swapify.service.authService

import com.yavs.swapify.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

class SpotifyAuthService : AuthService {
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

    private val authService = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(Constants.Spotify.AUTH_URL).build().create(SpotifyAuthApi::class.java)

    override fun getOAuthUrl(): String {
        val scope = "playlist-modify-private playlist-modify-public playlist-read-collaborative playlist-read-private user-read-private"
        return "${Constants.Spotify.AUTH_URL}/authorize?client_id=${Constants.Spotify.APP_ID}&response_type=code&redirect_uri=${Constants.Spotify.REDIRECT_URL}&scope=$scope&show_dialog=true"
    }

    override suspend fun getOAuthToken(code: String): String {
        return authService.getToken("Basic ${Constants.Spotify.APP_CRED}", code, Constants.Spotify.REDIRECT_URL, "authorization_code")
    }
}