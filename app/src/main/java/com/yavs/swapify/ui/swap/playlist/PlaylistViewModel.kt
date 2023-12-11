//package com.yavs.swapify.ui.swap.playlist
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.initializer
//import androidx.lifecycle.viewmodel.viewModelFactory
//import com.yavs.swapify.auth.TokenManager
//import com.yavs.swapify.data.model.Playlist
//import com.yavs.swapify.service.PlatformManager
//import com.yavs.swapify.ui.settings.SettingsViewModel
//import com.yavs.swapify.utils.Platform
//import kotlinx.coroutines.launch
//
//class PlaylistViewModel(private val tokenManager: TokenManager) : ViewModel(){
//    var playlist = MutableLiveData<List<Playlist>>()
//    var currentPlatform: Platform? = null
//    var transferPlatform: Platform? = null
//    private var tokens : MutableMap<String, String>? = mutableMapOf<String, String>()
//    fun setPlaylist(pos: Int){
//        viewModelScope.launch {
//            try{
//                playlist.postValue(PlatformManager.getService(currentPlatform!!).getPlaylists(tokens!![currentPlatform!!.accountName]!!))
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//    }
//
//    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                SettingsViewModel(
//                    TokenManager()
//                )
//            }
//        }
//    }
//}