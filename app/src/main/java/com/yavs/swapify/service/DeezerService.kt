package com.yavs.swapify.service

import com.yavs.swapify.data.model.Artist
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User
import com.yavs.swapify.service.authService.DeezerAuthService
import com.yavs.swapify.utils.Constants
import com.yavs.swapify.utils.Platform
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Inject


class DeezerService @Inject constructor() : PlatformService {
        private val deezerAuthService: PlatformService = DeezerAuthService() //delegation
        interface DeeezerApi{
            @GET("user/me")
            suspend fun getUser(
                @Query("access_token") accessToken: String
            ): Response<User>

            @GET("user/me/playlists")
            suspend fun getPlaylists(
                @Query("access_token") accessToken: String
            ): Response<List<Playlist>>

            @GET("search/track")
            suspend fun searchTrack(
                @Query("q") query: String,
            ): Response<List<Track>>

            @GET
            suspend fun getTracks(
                @Url url: String
            ): Response<List<Track>>
        }

        private val deezerApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.Deezer.BASE_URL).build().create(DeeezerApi:: class.java)

        override suspend fun getUser(token: String): User? {
            val response = deezerApi.getUser(token!!)
            return when(response.code()){
                200 -> {
                    response.body()
                }else ->{
                    User(platform = Platform.Deezer)
                }
            }
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
            return deezerAuthService.getOAuthUrl()
        }

        override suspend fun getOAuthToken(code: String): String {
            return deezerAuthService.getOAuthToken(code)
        }
}