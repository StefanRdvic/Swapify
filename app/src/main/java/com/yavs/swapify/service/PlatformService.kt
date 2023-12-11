package com.yavs.swapify.service

import com.yavs.swapify.data.model.Artist
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User

interface PlatformService {
    fun getUser(token:String?): Result<User>
    fun getTrack(trackId: Long): Track
    fun getArtist(artistId: Long): Artist
    fun getPlaylists(token: String): List<Playlist>
    fun searchTrack(title: String, artist: String): List<Track>
    fun getOAuthUrl(): String
    suspend fun getOAuthToken(code: String): String
}