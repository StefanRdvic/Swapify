package com.yavs.swapify.ui.loading

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavs.swapify.service.PlatformService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val services: Map<String, @JvmSuppressWildcards PlatformService>
) : ViewModel() {

//    @Inject
//    lateinit var services: Map<String, @JvmSuppressWildcards PlatformService>

    fun getUserToken(fromPlatFrom: String, toplatForm: String) {
        viewModelScope.launch {

            // check si le token est ?
            // si non -> appelle du service

//            try {
//                val service = services[fromPlatFrom]
//                val token = service?.getOAuthToken()
//                Log.d("token", token.toString())
//            } catch (e: Exception) {
//                Log.d("error", e.toString())
//            }
        }
    }
}