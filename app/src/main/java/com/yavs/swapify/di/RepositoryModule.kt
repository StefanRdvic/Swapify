package com.yavs.swapify.di

import android.content.Context
import android.content.SharedPreferences
import com.yavs.swapify.data.repository.SharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("Swapify", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideSharedPreferencesRepository(sharedPreferences: SharedPreferences): SharedPreferencesRepository {
        return SharedPreferencesRepository(sharedPreferences)
    }

}