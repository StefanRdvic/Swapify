package com.yavs.swapify.service

import com.yavs.swapify.model.Artist
import com.yavs.swapify.model.Playlist
import com.yavs.swapify.model.Track
import com.yavs.swapify.model.User
//import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject


class DeezerService @Inject constructor() : PlatformService {

        companion object{
            const val APP_ID: Long = 649821
            const val SECRET: String = "48e934960b566e8d508497542ba01205"
            const val REDIRECT_URL: String = "http://yavs.swapify/connect"
            const val BASE_URL = "https://api.deezer.com"
            const val AUTH_URL = "https://connect.deezer.com"
        }

        private val http = OkHttpClient()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build()

        interface DeezerAuthApi{
            @GET("oauth/access_token.php")
            suspend fun getCode(
                @Query("app_id") appId: String,
                @Query("redirect_uri") redirectUri: String,
                @Query("perms") perms: String
            ): Call<String>

            @GET("oauth/access_token.php")
            suspend fun getToken(
                @Query("app_id") appId: String,
                @Query("secret") secret: String,
                @Query("code") code: String
            ): Call<String>
        }

        private val authService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AUTH_URL).build().create(DeezerAuthApi::class.java)

        override fun getUser(token: String?): Result<User> {
            TODO("Not yet implemented")

        }


        override fun getTrack(trackId: Long): Track {
            TODO("Not yet implemented")

        }


        override fun getArtist(artistId: Long): Artist {
            TODO("Not yet implemented")

        }

        override fun getPlaylists(token: String): List<Playlist> {
            TODO("Not yet implemented")

        }

        override fun searchTrack(title: String, artist: String): List<Track> {
            TODO("Not yet implemented")

        }

        override fun getOAuthUrl(): String {
            TODO("Not yet implemented")

    //        return "https://connect.deezer.com/oauth/auth.php?app_id=$APP_ID&redirect_uri=$REDIRECT_URL&perms=basic_access,manage_library,offline_access"
        }



        override suspend fun getOAuthToken(code: String): String {
            val responseCode = authService
                .getCode(APP_ID.toString(), REDIRECT_URL, "basic_access,manage_library,offline_access")
                .awaitResponse()

            when(responseCode.code()){
                200 -> {
                    val response = responseCode.body()
                    val code = response?.substringAfter("code=")?.substringBefore("&expires=")
                    return authService.getToken(
                        APP_ID.toString(),
                        SECRET,
                        code.toString()).awaitResponse().body().toString()
                }
                else -> {
                    return ""
                }
            }
            //the response look like this : access_token=....&expires=.... parse it to get the code



            val token = authService.getToken(APP_ID.toString(), SECRET, code.toString())
        }
}