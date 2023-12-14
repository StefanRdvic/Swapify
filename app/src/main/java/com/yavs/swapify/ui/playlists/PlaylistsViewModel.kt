package com.yavs.swapify.ui.playlists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.repository.SharedPreferencesRepository
import com.yavs.swapify.service.PlatformService
import com.yavs.swapify.utils.Platform
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = PlaylistsViewModel.Factory::class)
class PlaylistsViewModel @AssistedInject constructor(
    private val services: Map<String, @JvmSuppressWildcards PlatformService>,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    @Assisted private val platform: Platform
) : ViewModel(){

    val playlists = MutableLiveData<List<Playlist>>()

    init {
        syncPlaylist()
    }

    private fun syncPlaylist(){
        viewModelScope.launch(Dispatchers.IO) {
            playlists.postValue(sharedPreferencesRepository.getString(platform.name)?.let { token ->
                services[platform.name.lowercase()]?.getPlaylists(token)
            }?: emptyList())
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(platform: Platform): PlaylistsViewModel
    }
}