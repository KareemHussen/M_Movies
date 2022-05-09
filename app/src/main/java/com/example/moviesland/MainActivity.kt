package com.example.moviesland

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import com.example.moviesland.Adapters.FragmentAdapter
import com.example.moviesland.util.Constants.Companion.Net
import com.example.moviesland.util.Constants.Companion.isInternetAvailable
import com.example.moviesland.util.Constants.Companion.isNetworkAvailable
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.InetAddress
import java.net.UnknownHostException


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportActionBar!!.hide();
//
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Net = isInternetAvailable(this) && isNetworkAvailable(this)

        swipeToRefresh.setOnRefreshListener {

            this.recreate()


            Net = isInternetAvailable(this) && isNetworkAvailable(this)

            swipeToRefresh.isRefreshing = false

        }






            Log.d("elnet" , Net.toString())



        mainViewPager.adapter = FragmentAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayout, mainViewPager) { tab, position ->

            when (position) {

                0 -> {
                    tab.text = "HOME"
                }

                1 -> {
                    tab.text = "MOVIE"
                }

                2 -> {
                    tab.text = "SERIES"
                }

                3 -> {
                    tab.text = "KIDS"
                }

                else -> {
                    tab.text = "LATER"
                }
            }

        }.attach()


    }



}


//