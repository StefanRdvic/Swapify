package com.yavs.swapify.ui.loading

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavs.swapify.data.model.Token
import com.yavs.swapify.data.repository.TokenRepository
import com.yavs.swapify.service.PlatformService
import com.yavs.swapify.utils.Platform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val services: Map<String, @JvmSuppressWildcards PlatformService>,
    private val tokenRepository: TokenRepository

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

                        val token = Token(queryParams["access_token"]!!)

                        tokenRepository.save(Platform.Deezer, token)
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
            "/spotify" -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val res = services[Platform.Spotify.name.lowercase()]!!.getOAuthToken(
                            data.getQueryParameter("code")!!
                        ).let { JSONObject(it) }

                        val token = Token(
                            res.getString("access_token"),
                            LocalDateTime
                                .now()
                                .plusSeconds(res.getLong("expires_in"))
                        )

                        tokenRepository.save(Platform.Spotify, token)
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