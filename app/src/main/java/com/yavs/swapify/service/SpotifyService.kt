package com.yavs.swapify.service

import com.google.gson.Gson
import com.yavs.swapify.api.SpotifyApi
import com.yavs.swapify.api.auth.SpotifyAuthApi
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User
import com.yavs.swapify.utils.Constants
import com.yavs.swapify.utils.Platform
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyService @Inject constructor(
    private val spotifyApi: SpotifyApi,
    private val spotifyAuthApi: SpotifyAuthApi
) : PlatformService {

    private val gson: Gson = Gson()

    override suspend fun getUser(token: String): User {
        val response = spotifyApi.getUser("Bearer $token")
        return response.body()?.toUser()?.also { it.isInit = true } ?: User(platform = Platform.Spotify)
    }

    override suspend fun getPlaylists(token: String): List<Playlist> {
        val response = spotifyApi.getPlaylists("Bearer $token")
        return response.body()?.items?.map { it.toPlaylist() } ?: emptyList()

    }

    override suspend fun getPlaylistTracks(token: String, playlistId: String): List<Track> {
        val response = spotifyApi.getPlaylistTracks(playlistId,"Bearer $token")
        return response.body()?.items?.mapNotNull { it.track?.toTrack() } ?: emptyList()
    }

    override suspend fun searchTrack(title: String, artist: String,token:String): Track? {
        val response = spotifyApi.searchTrack(
            "Bearer $token",
            "$title $artist",
            "track",
            1,
            "audio"
        )
        return response.body()?.tracks?.items?.map { it.toTrack() }?.getOrNull(0)
    }

    override suspend fun createPlaylistSwap(token: String, name: String, tracks: List<Track>): Boolean {
        val response = spotifyApi.createPlaylistSwap(
            "Bearer $token",
            gson
                .toJson(mapOf("name" to name))
                .toRequestBody("application/json; charset=utf-8".toMediaType())
        )

        return (response.isSuccessful) && spotifyApi.addTracks(
            response.body()?.id!!,
            "Bearer $token",
            gson.toJson(mapOf("uris" to tracks.map(Track::uri), "position" to 0))
                .toRequestBody("application/json; charset=utf-8".toMediaType())
        ).isSuccessful
    }

    override fun getOAuthUrl(): String {
        val scope = "playlist-modify-private playlist-modify-public playlist-read-collaborative playlist-read-private user-read-private"
        return "${Constants.Spotify.AUTH_URL}/authorize?client_id=${Constants.Spotify.APP_ID}&response_type=code&redirect_uri=${Constants.Spotify.REDIRECT_URL}&scope=$scope&show_dialog=true"
    }

    override suspend fun getOAuthToken(code: String): String {
        return spotifyAuthApi.getToken(
            "Basic ${Constants.Spotify.APP_CRED}",
            code,
            Constants.Spotify.REDIRECT_URL,
            "authorization_code"
        )
    }
}