package com.yavs.swapify.ui.playlists

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavs.swapify.data.model.Playlist
import com.yavs.swapify.data.repository.SharedPreferencesRepository
import com.yavs.swapify.service.PlatformService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val services: Map<String, @JvmSuppressWildcards PlatformService>,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel(){
    val playlists = MutableLiveData<List<Playlist>>()

    fun setPlaylist(platform: String){
        viewModelScope.launch(Dispatchers.IO) {
            val token = sharedPreferencesRepository.getString(platform)
            if (token != null) {
                playlists.postValue(services[platform]?.getPlaylists(token))
            }
        }
    }
}