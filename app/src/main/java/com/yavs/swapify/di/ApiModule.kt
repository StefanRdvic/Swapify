package com.yavs.swapify.di

import com.yavs.swapify.api.DeezerApi
import com.yavs.swapify.api.SpotifyApi
import com.yavs.swapify.api.auth.DeezerAuthApi
import com.yavs.swapify.api.auth.SpotifyAuthApi
import com.yavs.swapify.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideDeezerApi(): DeezerApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.Deezer.BASE_URL).build().create(DeezerApi:: class.java)
    }

    @Provides
    @Singleton
    fun provideSpotifyApi(): SpotifyApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.Spotify.BASE_URL).build().create(SpotifyApi:: class.java)
    }

    @Provides
    @Singleton
    fun provideDeezerAuthService(): DeezerAuthApi {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(Constants.Deezer.AUTH_URL).build().create(DeezerAuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSpotifyAuthService(): SpotifyAuthApi {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(Constants.Spotify.AUTH_URL).build().create(SpotifyAuthApi::class.java)
    }
}