package com.yavs.swapify.api

import com.yavs.swapify.data.model.spotify.SpotifyPlaylist
import com.yavs.swapify.data.model.spotify.SpotifyTrack
import com.yavs.swapify.data.model.spotify.SpotifyUser
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface SpotifyApi {
    data class Wrapper<T>(val items: T)
    data class TrackWrapper<T>(val track: T?)
    data class Search<T>(val tracks: Wrapper<T>)

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
        @Header("Authorization") authorization: String,
        @Query("q") title: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
        @Query("include_external") includeExternal: String
    ): Response<Search<List<SpotifyTrack>>>

    @Headers("Content-Type: application/json")
    @POST("v1/me/playlists")
    suspend fun createPlaylistSwap(
        @Header("Authorization") authorization : String,
        @Body name: RequestBody
    ): Response<SpotifyPlaylist>

    @GET("v1/playlists/{id}/tracks")
    suspend fun getPlaylistTracks(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
    ): Response<Wrapper<List<TrackWrapper<SpotifyTrack>>>>

    @Headers("Content-Type: application/json")
    @POST("v1/playlists/{id}/tracks")
    suspend fun addTracks(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
        @Body tracks: RequestBody
    ): Response<Any>
}