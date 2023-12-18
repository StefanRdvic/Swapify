package com.yavs.swapify.ui.playlists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.repository.TokenRepository
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
    private val tokenRepository: TokenRepository,
    @Assisted private val platform: Platform
) : ViewModel(){

    val playlists = MutableLiveData<List<Playlist>>()

    init {
        syncPlaylist()
    }

    private fun syncPlaylist(){
        viewModelScope.launch(Dispatchers.IO) {
            playlists.postValue(tokenRepository.get(platform)?.let { token ->
                    services[platform.name]?.getPlaylists(token.access)
                }?: emptyList()
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(platform: Platform): PlaylistsViewModel
    }
}