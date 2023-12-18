package com.yavs.swapify.service

import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User

interface PlatformService {
    suspend fun getUser(token:String): User
    suspend fun getPlaylists(token: String): List<Playlist>
    suspend fun searchTrack(title: String, artist: String, token : String): Track?
    suspend fun getPlaylistTracks(token: String, playlistId: String): List<Track>
    suspend fun createPlaylistSwap(token : String, name : String, tracks: List<Track>): Boolean
    suspend fun getOAuthToken(code: String): String
    fun getOAuthUrl(): String

}