package com.yavs.swapify.service

import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User
import com.yavs.swapify.data.model.spotify.SpotifyUser
import com.yavs.swapify.service.authService.SpotifyAuthService
import com.yavs.swapify.utils.Constants
import com.yavs.swapify.utils.Platform
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url
import javax.inject.Inject

class SpotifyService  @Inject constructor() : PlatformService {

    private val spotifyAuthService: PlatformService = SpotifyAuthService()
    interface SpotifyApi{
        @GET("v1/me")
        suspend fun getUser(
            @Header("Authorization") accessToken: String
        ): Response<SpotifyUser>

        @GET("user/me/playlists")
        suspend fun getPlaylists(
            @Query("access_token") accessToken: String
        ): Response<DeezerService.Wrapper<List<Playlist>>>

        @GET("search/track")
        suspend fun searchTrack(
            @Query("q") query: String,
        ): Response<List<Track>>

        @GET
        suspend fun getTracks(
            @Url url: String
        ): Response<DeezerService.Wrapper<List<Track>>>
    }

    private val spotifyApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.Spotify.BASE_URL).build().create(SpotifyApi:: class.java)


    override suspend fun getUser(token: String): User {
        val response = spotifyApi.getUser("Bearer $token")

        return if (response.isSuccessful) response.body()!!
            .toUser() else User(platform = Platform.Spotify)
    }

    override suspend fun getPlaylists(token: String): List<Playlist> {
        TODO("Not yet implemented")
    }

    override suspend fun searchTrack(title: String, artist: String): List<Track> {
        TODO("Not yet implemented")
    }

    override fun getOAuthUrl(): String {
        return spotifyAuthService.getOAuthUrl()
    }

    override suspend fun getOAuthToken(code: String): String {
        return spotifyAuthService.getOAuthToken(code)
    }
}