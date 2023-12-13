package com.yavs.swapify.service

import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User

interface PlatformService {
    suspend fun getUser(token:String): User
    suspend fun getPlaylists(token: String): List<Playlist>
    suspend fun searchTrack(title: String, artist: String): List<Track>
    fun getOAuthUrl(): String
    suspend fun getOAuthToken(code: String): String
}