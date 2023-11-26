package com.yavs.swapify

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yavs.swapify.service.AuthService
import com.yavs.swapify.utils.Platform


class MainActivity : AppCompatActivity() {
    private lateinit var authService: AuthService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navController =  Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(bottomNav,navController)
        authService = AuthService(this)
        checkOAuth(intent?.data)

    }

    private fun checkOAuth(data: Uri?) {
        if(data!=null && data.toString().startsWith("http://swapify.yavs")){
            data.getQueryParameter("code")?.let { authService.saveToken(Platform.Deezer.accountName, it) }
            data.getQueryParameter("error")?.let {
                Toast.makeText(this,it,Toast.LENGTH_SHORT)
            }
        }
    }
}