package com.yavs.swapify.service.authService

import com.yavs.swapify.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class DeezerAuthService: AuthService {

    interface DeezerAuthApi{
        @GET("/oauth/access_token.php")
        suspend fun getToken(
            @Query("app_id") appId: String,
            @Query("secret") secret: String,
            @Query("code") code: String
        ): String
    }

    private val authService = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(Constants.Deezer.AUTH_URL).build().create(DeezerAuthApi::class.java)

    override fun getOAuthUrl(): String {
        return "${Constants.Deezer.AUTH_URL}/oauth/auth.php?app_id=${Constants.Deezer.APP_ID}&redirect_uri=${Constants.Deezer.REDIRECT_URL}&perms=basic_access,manage_library,offline_access"
    }

    override suspend fun getOAuthToken(code: String): String {
        return authService
            .getToken(Constants.Deezer.APP_ID.toString(), Constants.Deezer.SECRET, code)
    }

}