package com.yavs.swapify.service

import api.deezer.DeezerApi
import api.deezer.objects.Permission
import com.yavs.swapify.utils.appId
import com.yavs.swapify.utils.redirectUri
import com.yavs.swapify.utils.secret


class DeezerService {

    private val deezerApi: DeezerApi = DeezerApi()

    fun getLoginUrl(): String{
        return deezerApi.auth().getLoginUrl(appId,redirectUri,Permission.MANAGE_LIBRARY,Permission.BASIC_ACCESS)
    }

    fun getToken(code: String): String {
        return deezerApi.auth().getAccessToken(appId, secret, code).executeAsync().get().accessToken
    }

}