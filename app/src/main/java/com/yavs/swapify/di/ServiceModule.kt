package com.yavs.swapify.di

import com.yavs.swapify.service.DeezerService
import com.yavs.swapify.service.PlatformService
import com.yavs.swapify.service.SpotifyService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey


@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @IntoMap
    @StringKey("spotify")
    abstract fun provideSpotifyService(spotifyService: SpotifyService): PlatformService

    @Binds
    @IntoMap
    @StringKey("deezer")
    abstract fun provideDeezerService(deezerService: DeezerService): PlatformService
}