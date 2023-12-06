package com.yavs.swapify.service

import com.yavs.swapify.model.Artist
import com.yavs.swapify.model.Playlist
import com.yavs.swapify.model.Track
import com.yavs.swapify.model.User
import com.yavs.swapify.utils.Platform
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class SpotifyService : PlatformService {

    private val http = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    companion object{
        const val CLIENT_ID: String = "a338c974499c4b21a1d151969fa907b4"
        const val REDIRECT_URL: String = "http://yavs.swapify/connect"
        const val SCOPE: String = "user-read-private"
        const val BASE_URL = "https://api.spotify.com"
    }

    override fun getUser(token: String?): Result<User> {
        val req = Request.Builder().url("$BASE_URL/user/me?access_token=$token").build()
        val call: Call = http.newCall(req)
        val response: Response = call.execute()
        if (response.code==200){
            val user = json.decodeFromString<User>(response.body?.string()!!)
            user.platform=Platform.Deezer
            return Result.success(user)
        }
        return Result.failure(Exception("No user found"))
    }

    override fun getTrack(trackId: Long): Track {
        TODO("Not yet implemented")
    }

    override fun getArtist(artistId: Long): Artist {
        TODO("Not yet implemented")
    }

    override fun getPlaylists(token: String): List<Playlist> {
        TODO("Not yet implemented")
    }

    override fun searchTrack(title: String, artist: String): List<Track> {
        TODO("Not yet implemented")
    }

    override fun getOAuthUrl(): String {
        return "https://connect.deezer.com/oauth/auth.php?app_id=$CLIENT_ID&redirect_uri=$REDIRECT_URL&perms=basic_access,manage_library,offline_access"
    }

    override fun getOAuthToken(code: String): String {
        try {
            val req = Request.Builder().url("https://accounts.spotify.com/authorize?client_id=$CLIENT_ID&response_type=code&redirect_uri=$REDIRECT_URL&scope=$SCOPE").build()
            val call: Call = http.newCall(req)
            val response: Response = call.execute()
            val r = response.body?.string()
            val token = r?.split("&")?.find { param -> param.startsWith("access_token=") }?.substringAfter("access_token=")
            return token!!
        }catch (e: Exception) {
            throw Exception("Error retrieving token",e.cause)
        }
    }
}