package com.yavs.swapify.service

import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User
import com.yavs.swapify.data.model.spotify.SpotifyUser
import com.yavs.swapify.data.model.spotify.SpotifyPlaylist
import com.yavs.swapify.data.model.spotify.SpotifyTrack
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

    data class Wrapper<T>(val items: T)

    interface SpotifyApi{
        @GET("v1/me")
        suspend fun getUser(
            @Header("Authorization") accessToken: String
        ): Response<SpotifyUser>

        @GET("/v1/me/playlists")
        suspend fun getPlaylists(
            @Header("Authorization") authorization: String
        ): Response<Wrapper<List<SpotifyPlaylist>>>

        @GET("v1/search?")
        suspend fun searchTrack(
            @Query("q") title: String,
            @Query("type")type:String,
            @Query("limit")limit:Int,
            @Query("include_external")includeExternal:String,
            @Header("Authorization")authorization:String
        ): Response<Wrapper<SpotifyTrack>>

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
            .toUser().also { it.isInit = true } else User(platform = Platform.Spotify)
    }

    override suspend fun getPlaylists(token: String): List<Playlist> {
        val response = spotifyApi.getPlaylists("Bearer $token")
        return (if(response.isSuccessful) response.body()!!.items.map { it.toPlaylist() } else emptyList())

    }

    override suspend fun searchTrack(title: String, artist: String,token:String): Track {
        val response = spotifyApi.searchTrack(
        "$title $artist","track",
        1,"audio",
        "Bearer $token")
        return (if(response.isSuccessful) { println(response.body()!!.items.toTrack().artistName); response.body()!!.items.toTrack();}
        else Track())

    }

    override fun getOAuthUrl(): String {
        return spotifyAuthService.getOAuthUrl()
    }

    override suspend fun getOAuthToken(code: String): String {
        return spotifyAuthService.getOAuthToken(code)
    }
}