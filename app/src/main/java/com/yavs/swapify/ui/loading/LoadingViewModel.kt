package com.yavs.swapify.ui.loading

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavs.swapify.data.repository.SharedPreferencesRepository
import com.yavs.swapify.service.PlatformService
import com.yavs.swapify.utils.Platform
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
                        val res = services[Platform.Deezer.name.lowercase()]!!.getOAuthToken(
                            data.getQueryParameter("code")!!
                        )
                        val queryParams = res.split("&").associate {
                            val (k, v) = it.split("=")
                            k to v
                        }

                        sharedPreferencesRepository.putString(Platform.Deezer.name, queryParams["access_token"]!!)
                    } catch (e: Exception) {
                        // todo : create a status object to handle errors
                        withContext(Dispatchers.Main) {
                            Log.e("intent", e.toString())
                        }
                    } finally {
                        // todo : post the futur status object
                        navigationEvent.postValue(true)
                    }

                }
            }
        }

    }
}