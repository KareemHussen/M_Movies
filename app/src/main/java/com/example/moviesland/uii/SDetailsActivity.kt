package com.example.moviesland.uii

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesland.Adapters.SimilarSeriesAdapter
import com.example.moviesland.Fragments.BottomSheet
import com.example.moviesland.MainActivity
import com.example.moviesland.R
import com.example.moviesland.Models.SeriesModel.Details.Season
import com.example.moviesland.Models.SeriesModel.SeriesResult
import com.example.moviesland.util.Genres
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.d_series_activity.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.InetAddress
import java.util.concurrent.TimeUnit

class SDetailsActivity : AppCompatActivity() {

    lateinit var viewModel : ViewModel
    lateinit var similarAdapter : SimilarSeriesAdapter


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.d_series_activity)

//        supportActionBar!!.hide()

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)


        viewModel = ViewModelProvider(this).get(ViewModel::class.java)


        val intent = intent

        val series = intent.getParcelableExtra<SeriesResult>("series") as SeriesResult

        if (checkConnection()){
            viewModel.getSeriesDetails(series.id!!)
            viewModel.getSimilarSeries(series.id!!)

        }

        val stringBuilder = StringBuilder()

        stitlee.text = series.name

        if (series.poster_path == null){
            simageView.setImageResource(R.drawable.notimgicon)
        }else {
            Glide.with(this).load("https://image.tmdb.org/t/p/original${series.poster_path}").into(simageView)

        }

        sYear.text = series.first_air_date?.substring(0 , 4)
        sstory.text = series.overview
        srate.text = series.vote_average.toString().substring(0,3)

        viewModel.SeriesDetailsMutable.observe(this, Observer {

            numbOfSeasons.text = it.number_of_seasons.toString()

        })

        for (i in series?.genre_ids!!){

            val s = Genres.getGenres(i)

            stringBuilder.append("${s} ")

        }

        sGenre.text = stringBuilder.toString()

        setupRecyclerView()


        if (checkConnection()){

            viewModel.getSimilarSeries(series.id!!)

        }
        Log.d("ya3m" , series.id.toString())



        viewModel.similarSeriesMutable.observe(this , Observer {
            similarAdapter.submitList(it.results)
        })






        similarAdapter.setOnItemClickListner {

            if (checkConnection() ){

                val intent = Intent(this , SDetailsActivity::class.java)
                intent.putExtra("series" , it)

                startActivity(intent)

            } else {

                Toast.makeText(this , "No Internet Connection Found" , Toast.LENGTH_LONG).show()

            }

        }




        sfab.setOnClickListener {
            if (checkConnection() ){

                viewModel.SeriesDetailsMutable.observe(this, Observer {

                    var list : ArrayList<Season> = ArrayList()
                    for ( i in it.seasons){
                        if (i.season_number != 0){
                            list.add(i)
                        }
                    }
                    var bottomSheet = BottomSheet(list , series)
                    bottomSheet.show(supportFragmentManager , "TAG")



                })


            } else {

                Toast.makeText(this , "No Internet Connection Found" , Toast.LENGTH_LONG).show()

            }
        }


        sfavButton.setOnClickListener {

            Snackbar.make(it , "Series saved successfully" , Snackbar.LENGTH_LONG).show()

        }






    }

    override fun onBackPressed() {
        var intent = Intent(this , MainActivity ::class.java)
        startActivity(intent)
        finish()

//        super.onBackPressed()

    }

    private fun setupRecyclerView(){
        similarAdapter = SimilarSeriesAdapter()
        srvDetails.layoutManager = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL,false)
        srvDetails.adapter = similarAdapter


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

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun isInternetAvailable(): Boolean {
//        return try {
//            val command = "ping -c 1 google.com"
//            Runtime.getRuntime().exec(command).waitFor(1000 , TimeUnit.MILLISECONDS) == true
//        } catch (e: Exception) {
//            false
//        }
//    }


}