package com.yavs.swapify.ui.loading

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavs.swapify.data.repository.SharedPreferencesRepository
import com.yavs.swapify.service.PlatformService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val services: Map<String, @JvmSuppressWildcards PlatformService>,
    private val sharedPreferencesRepository: SharedPreferencesRepository

) : ViewModel() {

    val navigationEvent = MutableLiveData<Boolean>()

    fun handleNewIntentData(data: Uri?) {
        Log.i("intent", data.toString())


        when(data?.path){
            "/deezer" -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val code = data.getQueryParameter("code") ?: throw IllegalStateException("Code not found")
                        val service = services["deezer"] ?: throw IllegalStateException("Service not found")
                        val response = service.getOAuthToken(code)

                        val queryParams = response.split("&").associate {
                            val parts = it.split("=")
                            if (parts.size == 2) parts[0] to parts[1] else throw IllegalArgumentException("Invalid response format")
                        }

                        val accessToken = queryParams["access_token"] ?: throw IllegalStateException("Access token not found")

                        withContext(Dispatchers.Main) {
                            sharedPreferencesRepository.putString("deezer", accessToken)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.e("intent", e.toString())
                        }
                    } finally {
                        navigationEvent.postValue(true)
                    }

                }
            }
        }

    }
}