package com.yavs.swapify.api

import com.yavs.swapify.data.model.User
import com.yavs.swapify.data.model.deezer.DeezerPlaylist
import com.yavs.swapify.data.model.deezer.DeezerTrack
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface DeezerApi {
    data class Wrapper<T>(val data: T)

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