package com.example.moviesland.uii

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesland.Adapters.HomeMovieAdapter
import com.example.moviesland.MainActivity
import com.example.moviesland.Models.MovieModel.Result
import com.example.moviesland.R
import com.example.moviesland.util.Genres
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.movie_item.view.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.InetAddress
import java.util.concurrent.TimeUnit

class DetailsActiviy : AppCompatActivity() {

    lateinit var viewModel : ViewModel
    lateinit var similarMovieAdapter : HomeMovieAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)



        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val intent = intent

        val movie = intent.getParcelableExtra<Result>("movie") as Result

        Log.d("kimo" , movie.title.toString())

        val stringBuilder = StringBuilder()

        titlee.text = movie.title
        if (movie.poster_path == null){
            imageView.setImageResource(R.drawable.notimgicon)
        }else {
            Glide.with(this).load("https://image.tmdb.org/t/p/original${movie.poster_path}").into(imageView)

        }
        Year.text = movie.release_date!!.substring(0 , 4)
        story.text = movie.overview
        rate.text = movie.vote_average.toString().substring(0,3)

        for (i in movie?.genre_ids!!){

            val s = Genres.getGenres(i)

            stringBuilder.append("${s} ")

        }

        Genre.text = stringBuilder.toString()

        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        if (checkConnection() ){
            viewModel.getSimilar(movie.id!!)

        }

        viewModel.similarMutable.observe(this , Observer {
            similarMovieAdapter.submitList(it.results)
        })

        similarMovieAdapter.setOnItemClickListner {

            if (checkConnection() ){


                val intent = Intent(this , DetailsActiviy::class.java)
                intent.putExtra("movie" , it)

                startActivity(intent)

            } else {

                Toast.makeText(this , "No Internet Connection Found" , Toast.LENGTH_LONG).show()

            }


        }

//

        SfavButton.setOnClickListener {


            viewModel.upsert(this , movie)
            Snackbar.make(it , "Movie saved successfully" , Snackbar.LENGTH_LONG).show()



        }




    }

    private fun setupRecyclerView(){
        similarMovieAdapter = HomeMovieAdapter()
        rvDetails.layoutManager = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL,false)
        rvDetails.adapter = similarMovieAdapter
    }

    override fun onBackPressed() {
        var intent = Intent(this , MainActivity ::class.java)
        startActivity(intent)
        finish()

//        super.onBackPressed()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkConnection(): Boolean {
        var result = false
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun isInternetAvailable(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor(1000 , TimeUnit.MILLISECONDS) == true
        } catch (e: Exception) {
            false
        }
    }

}