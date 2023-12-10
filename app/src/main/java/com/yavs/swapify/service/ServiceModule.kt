package com.yavs.swapify.service

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class, SingletonComponent::class)
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