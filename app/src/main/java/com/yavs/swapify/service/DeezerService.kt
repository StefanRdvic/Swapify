package com.yavs.swapify.service

import com.yavs.swapify.api.DeezerApi
import com.yavs.swapify.api.auth.DeezerAuthApi
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User
import com.yavs.swapify.utils.Constants
import com.yavs.swapify.utils.Platform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeezerService @Inject constructor(
    private val deezerAuthApi: DeezerAuthApi,
    private val deezerApi: DeezerApi
) : PlatformService {

    override suspend fun getUser(token: String): User {
        val response = deezerApi.getUser(token)

        return (response.body()?.also { it.isInit = true } ?: User())
            .also { it.platform = Platform.Deezer }
    }

    override suspend fun getPlaylists(token: String): List<Playlist> {
        val response = deezerApi.getPlaylists(token)

        return response.body()?.data?.map { it.toPlaylist() } ?: emptyList()
    }

    override suspend fun getPlaylistTracks(token: String, playlistId: String): List<Track> {
        val response = deezerApi.getPlaylistTracks(playlistId)
        return response.body()?.data?.map { it.toTrack() } ?: emptyList()
    }

    override suspend fun searchTrack(title: String, artist: String,token : String):Track? {
        val response = deezerApi.searchTrack("artist:\"$artist\" track:\"$title\"")
        return response.body()?.data?.map{ it.toTrack() }?.getOrNull(0)
    }

    override suspend fun createPlaylistSwap(token: String, name: String, tracks: List<Track>): Boolean {
        val response = deezerApi.createPlaylist(token, name)

        return response.isSuccessful && deezerApi.addTracks(
            response.body()?.id!!.toString(),
            token,
            tracks.joinToString { it.id }
        ).isSuccessful
    }

    override fun getOAuthUrl(): String {
        return "${Constants.Deezer.AUTH_URL}/oauth/auth.php?app_id=${Constants.Deezer.APP_ID}&redirect_uri=${Constants.Deezer.REDIRECT_URL}&perms=basic_access,manage_library,offline_access"
    }

    override suspend fun getOAuthToken(code: String): String {
        return deezerAuthApi
            .getToken(Constants.Deezer.APP_ID.toString(), Constants.Deezer.SECRET, code)
    }
}