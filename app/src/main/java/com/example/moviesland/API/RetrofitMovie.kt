package com.example.moviesland.API

import com.example.moviesland.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitMovie() {

    companion object {

        private val client = OkHttpClient()

        private val retrofit =  Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build()

        val api = retrofit.create(MoviesAPI::class.java)

    }






}