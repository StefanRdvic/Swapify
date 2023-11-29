package com.yavs.swapify.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.yavs.swapify.utils.Platform


class TokenManager() {
    companion object{
        const val OAUTH_TOKEN = "oauth_token"
    }

    fun saveToken(context: Context, token: String, platform: Platform) {
        try {
            val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                context,
                OAUTH_TOKEN,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            val editor = sharedPreferences.edit()
            editor.putString(platform.accountName, token)
            editor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getToken(context: Context, platform: Platform): String? {
        return try {
            val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                context,
                OAUTH_TOKEN,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            sharedPreferences.getString(platform.accountName, null)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun removeToken(context: Context, platform: Platform) {
        try {
            val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                context,
                OAUTH_TOKEN,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            sharedPreferences.edit().remove(platform.accountName).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}