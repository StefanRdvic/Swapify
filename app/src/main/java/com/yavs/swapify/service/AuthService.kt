package com.yavs.swapify.service

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import com.yavs.swapify.SettingsFragment
import com.yavs.swapify.utils.ACCOUNT_TYPE
import com.yavs.swapify.utils.TOKEN_TYPE

class AuthService(private val context: Context) {

    private val am: AccountManager = AccountManager.get(context)
    fun saveToken(accountName: String, token: String){
        val account = Account(accountName, ACCOUNT_TYPE)
        am.setAuthToken(account, TOKEN_TYPE, token)
    }

    fun getAuthToken(accountName: String): String? {
        val a = am.getAccountsByType(ACCOUNT_TYPE)

        val account = Account(accountName, ACCOUNT_TYPE)

        return am.peekAuthToken(account, TOKEN_TYPE)
    }

    fun removeToken(accountName: String){
        val account = Account(accountName, ACCOUNT_TYPE)
        am.removeAccount(account,null,null,null)
    }

}