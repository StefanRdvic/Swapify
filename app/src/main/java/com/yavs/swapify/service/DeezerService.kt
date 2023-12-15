package com.yavs.swapify.service

import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User
import com.yavs.swapify.data.model.deezer.DeezerPlaylist
import com.yavs.swapify.data.model.deezer.DeezerTrack
import com.yavs.swapify.service.authService.DeezerAuthService
import com.yavs.swapify.utils.Constants
import com.yavs.swapify.utils.Platform
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject


class DeezerService @Inject constructor() : PlatformService {
        private val deezerAuthService: PlatformService = DeezerAuthService() //delegation
        private val deezerApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.Deezer.BASE_URL).build().create(DeeezerApi:: class.java)

        data class Wrapper<T>(val data: T)

        interface DeeezerApi{
            @GET("user/me")
            suspend fun getUser(
                @Query("access_token") accessToken: String
            ): Response<User>

            @GET("user/me/playlists")
            suspend fun getPlaylists(
                @Query("access_token") accessToken: String
            ): Response<Wrapper<List<DeezerPlaylist>>>

            @GET("/playlist/{id}/tracks")
            suspend fun getPlaylistTracks(
                @Path("id") id:String
            ): Response<Wrapper<List<DeezerTrack>>>

            @GET("search/track")
            suspend fun searchTrack(
                @Query("q") query: String,
            ): Response<Wrapper<List<DeezerTrack>>>

            @POST("user/me/playlists")
            suspend fun createPlaylist(
                @Query("access_token") accessToken: String,
                @Query("title") name: String
            ): Response<DeezerPlaylist>

            @POST("playlist/{id}/tracks")
            suspend fun addTracks(
                @Path("id") playlistId: String,
                @Query("access_token") accessToken: String,
                @Query("songs") songs: String,
            ): Response<Any>
        }



        override suspend fun getUser(token: String): User {
            val response = deezerApi.getUser(token)

            val user = if(response.isSuccessful) response.body()!!.also { it.isInit = true } else User()

            return user.also { it.platform = Platform.Deezer }
        }

        override suspend fun getPlaylists(token: String): List<Playlist> {
            val response = deezerApi.getPlaylists(token)

            return (if(response.isSuccessful) response.body()!!.data.map { it.toPlaylist() } else emptyList())
        }

        override suspend fun getPlaylistTracks(token: String, playlistId: String): List<Track> {
            val response = deezerApi.getPlaylistTracks(playlistId)
            return (if(response.isSuccessful) response.body()!!.data.map { it.toTrack() } else emptyList())
        }

        override suspend fun searchTrack(title: String, artist: String,token : String):Track? {
            val response = deezerApi.searchTrack("artist:\"$artist\" track:\"$title\"")
            return (if(response.isSuccessful) response.body()?.data?.map{ it.toTrack() }?.getOrNull(0) else null)
        }

        override suspend fun createPlaylistSwap(token: String, name: String, tracks: List<Track>): Boolean {
            val response = deezerApi.createPlaylist(token,name)
            val id = if(response.isSuccessful) response.body()?.id.toString() else return false
            val trackResponse = deezerApi.addTracks(id,token,tracks.joinToString { it.id })
            return trackResponse.isSuccessful
        }

        override fun getOAuthUrl(): String {
            return deezerAuthService.getOAuthUrl()
        }

        override suspend fun getOAuthToken(code: String): String {
            return deezerAuthService.getOAuthToken(code)
        }
}