package com.yavs.swapify.ui.tracks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavs.swapify.data.model.Track
import com.yavs.swapify.data.repository.TokenRepository
import com.yavs.swapify.service.PlatformService
import com.yavs.swapify.utils.Platform
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TracksViewModel.Factory::class)
class TracksViewModel @AssistedInject constructor(
    private val services: Map<String, @JvmSuppressWildcards PlatformService>,
    private val tokenRepository: TokenRepository,
    @Assisted("from") private val from: Platform,
    @Assisted("to") private val to: Platform,
    @Assisted("playlistId") private val playlistId: String,
    @Assisted("playlistName") private val playlistName: String
) : ViewModel(){
    val tracks = MutableLiveData<List<Track>>()
    val playlistCreated = MutableLiveData<Boolean>()

    init {
        searchPlaylistTracks()
    }
    private fun searchPlaylistTracks(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = tokenRepository.get(from)?.let { services[from.name.lowercase()]?.getPlaylistTracks(it.access,playlistId) } ?: emptyList()
            val searched = mutableListOf<Track>()
            for (track in list){
                val toTrack = tokenRepository.get(to)?.let { services[to.name.lowercase()]?.searchTrack(track.title!!,
                    track.artistName!!, it.access) }
                if (toTrack != null) {
                    searched.add(toTrack)
                }
            }
            Log.i("d",searched.toString())
            tracks.postValue(searched)
        }
    }

    fun createPlaylistSwap(tracks: List<Track>)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val isCreated = tokenRepository.get(to)?.let { services[to.name.lowercase()]?.createPlaylistSwap(it.access,playlistName,tracks) } ?: false
            playlistCreated.postValue(isCreated)
        }
    }



    @AssistedFactory
    interface Factory {
        fun create(@Assisted("from") from: Platform, @Assisted("to") to: Platform, @Assisted("playlistId")playlistId: String,@Assisted("playlistName")playlistName: String): TracksViewModel
    }
}