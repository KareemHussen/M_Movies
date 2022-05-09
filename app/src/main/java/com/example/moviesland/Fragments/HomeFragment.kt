package com.example.moviesland.Fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesland.Adapters.HomeMovieAdapter

import com.example.moviesland.MainActivity
import com.example.moviesland.R
import com.example.moviesland.uii.DetailsActiviy
import com.example.moviesland.uii.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_layout.*
import android.view.MotionEvent
import android.widget.AbsListView
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.recyclerview.widget.RecyclerView
import com.example.moviesland.Adapters.HomeSeriesAdapter
import com.example.moviesland.uii.SDetailsActivity
import com.example.moviesland.util.Constants
import java.lang.Exception
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment(R.layout.home_layout) {
    lateinit var viewModel: ViewModel
    lateinit var homeMovieAdapter: HomeMovieAdapter
    lateinit var homeSeriesAdapter: HomeSeriesAdapter

    var isScrolling: Boolean = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        requireActivity().bigImg.setImageResource(R.drawable.home2)


    }

    override fun onStart() {
        super.onStart()
        requireActivity().bigImg.setImageResource(R.drawable.home2)

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        
        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

//        homeProgressBar.visibility = View.VISIBLE


        if ( Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext())) {

            viewModel.homeMovies()
            viewModel.homeSeries()


        } else {

            Toast.makeText(requireContext(), "No Internet Connection Found", Toast.LENGTH_LONG)
                .show()

        }


        homeMovieAdapter.setOnItemClickListner {
            if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext())) {

                val intent = Intent(activity, DetailsActiviy::class.java)
                intent.putExtra("movie", it)

                startActivity(intent)
            } else {

                Toast.makeText(requireContext(), "No Internet Connection Found", Toast.LENGTH_LONG)
                    .show()

            }


        }

        homeSeriesAdapter.setOnItemClickListner {

            if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext())) {


                val intent = Intent(activity, SDetailsActivity::class.java)
                intent.putExtra("series", it)

                startActivity(intent)
            } else {

                Toast.makeText(requireContext(), "No Internet Connection Found", Toast.LENGTH_LONG)
                    .show()

            }


        }




        viewModel.HomeMoviesMutable.observe(viewLifecycleOwner, Observer {
            Log.d("sui", it.results!!.get(1).title.toString())

            homeMovieAdapter.submitList(it.results)

        })

        viewModel.HomeSeriesMutable.observe(viewLifecycleOwner, Observer {

            homeSeriesAdapter.submitList(it.results)

        })




        rvHomeMovie.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

            override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {

                val action = event.action

                when (action) {
                    MotionEvent.ACTION_DOWN -> rvHomeMovie.parent
                        .requestDisallowInterceptTouchEvent(true)
                }

                return false

            }

            override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {}

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })


        rvHomeMovie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

                    isScrolling = true

                }
            }


        })


        rvHomeSeries.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

            override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {

                val action = event.action

                when (action) {
                    MotionEvent.ACTION_DOWN -> rvHomeMovie.parent
                        .requestDisallowInterceptTouchEvent(true)
                }

                return false

            }

            override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {}

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })


        rvHomeSeries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

                    isScrolling = true

                }
            }


        })


    }

    private fun setupRecyclerView() {
        homeMovieAdapter = HomeMovieAdapter()
        rvHomeMovie.layoutManager =
            LinearLayoutManager(MainActivity(), LinearLayoutManager.HORIZONTAL, false)
        rvHomeMovie.adapter = homeMovieAdapter


        homeSeriesAdapter = HomeSeriesAdapter()
        rvHomeSeries.layoutManager =
            LinearLayoutManager(MainActivity(), LinearLayoutManager.HORIZONTAL, false)
        rvHomeSeries.adapter = homeSeriesAdapter
    }


}