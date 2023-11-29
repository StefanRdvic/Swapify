package com.yavs.swapify

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yavs.swapify.service.DeezerService
import com.yavs.swapify.utils.Platform


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navController =  Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(bottomNav,navController)
        checkOAuth(intent?.data)
    }

    private fun checkOAuth(data: Uri?) {

        if(data!=null && data.toString().startsWith(DeezerService.REDIRECT_URL)){
            data.getQueryParameter("code")?.let {
                viewModel.saveToken(this,it, Platform.Deezer)
                Log.i("saved token","saved token")
                Toast.makeText(this,"Successful Connection to deezer", Toast.LENGTH_SHORT).show()
            }
            data.getQueryParameter("error_reason")?.let {
                Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
            }
        }
    }


}