package com.example.moviesland.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class Constants {
    companion object{

        val BASE_URL = "https://api.themoviedb.org/3/"

        val API_KEY = "1d403ee01b18cbb4eb2147e4faa8e94c"

        var Net : Boolean = false

        @RequiresApi(Build.VERSION_CODES.M)
        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            connectivityManager?.let {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            }
            return result
        }

        fun isNetworkAvailable(context: Context) : Boolean{
            try {
                val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                var networkInfo : NetworkInfo? = null
                if (manager != null){
                    networkInfo = manager.activeNetworkInfo
                }
                return networkInfo != null && networkInfo.isConnected
            } catch (e : Exception){
                return false
            }
        }

    }








}