package com.yavs.swapify.service

import android.util.Log
import com.yavs.swapify.data.model.Artist
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.model.User
import com.yavs.swapify.service.authService.DeezerAuthService
import com.yavs.swapify.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class DeezerService @Inject constructor() : PlatformService {
        private val deezerAuthService: PlatformService = DeezerAuthService() //delegation

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.Deezer.BASE_URL).build()


        override fun getUser(token: String?): Result<User> {
            Log.i("test cass", "here")
            TODO("Not yet implemented")
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
            return deezerAuthService.getOAuthUrl()
        }

        override suspend fun getOAuthToken(code: String): String {
            return deezerAuthService.getOAuthToken(code)
        }
}