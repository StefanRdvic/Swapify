package com.yavs.swapify.service

import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User
import com.yavs.swapify.service.authService.AuthService

interface PlatformService : AuthService {
    suspend fun getUser(token:String): User
    suspend fun getPlaylists(token: String): List<Playlist>
    suspend fun searchTrack(title: String, artist: String, token : String): Track?
    suspend fun getPlaylistTracks(token: String, playlistId: String): List<Track>
    suspend fun createPlaylistSwap(token : String, name : String, tracks: List<Track>): Boolean

}