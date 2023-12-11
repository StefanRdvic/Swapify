package com.yavs.swapify.service.authService

import com.yavs.swapify.data.model.Artist
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User
import com.yavs.swapify.service.PlatformService
import com.yavs.swapify.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class DeezerAuthService: PlatformService {

    interface DeezerAuthApi{
        @GET("/oauth/access_token.php")
        suspend fun getToken(
            @Query("app_id") appId: String,
            @Query("secret") secret: String,
            @Query("code") code: String
        ): String
    }

    private val authService = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(Constants.Deezer.AUTH_URL).build().create(DeezerAuthApi::class.java)

    override fun getOAuthUrl(): String {
        return "https://connect.deezer.com/oauth/auth.php?app_id=${Constants.Deezer.APP_ID}&redirect_uri=${Constants.Deezer.REDIRECT_URL}&perms=basic_access,manage_library,offline_access"
    }

    override suspend fun getOAuthToken(code: String): String {
        return authService
            .getToken(Constants.Deezer.APP_ID.toString(), Constants.Deezer.SECRET, code)
    }

    override fun getUser(token: String?): Result<User> {
        throw NotImplementedError()
    }

    override fun getTrack(trackId: Long): Track {
        throw NotImplementedError()
    }

    override fun getArtist(artistId: Long): Artist {
        throw NotImplementedError()
    }

    override fun getPlaylists(token: String): List<Playlist> {
        throw NotImplementedError()
    }

    override fun searchTrack(title: String, artist: String): List<Track> {
        throw NotImplementedError()
    }

}