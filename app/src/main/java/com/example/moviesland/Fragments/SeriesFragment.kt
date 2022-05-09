package com.example.moviesland.Fragments

import DiscoverSeriesAdapter
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesland.R
import com.example.moviesland.uii.ViewModel
import com.example.moviesland.uii.SDetailsActivity
import com.example.moviesland.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_series.*
import java.lang.Exception
import java.net.InetAddress
import java.util.concurrent.TimeUnit

class SeriesFragment : Fragment(R.layout.fragment_series) {


    lateinit var viewModel : ViewModel
    lateinit var discoverSeriesAdapter: DiscoverSeriesAdapter
    lateinit var layoutManager: GridLayoutManager
    var isLoading = false
    var isScrolling = false
    var isLastPage = false
    var Net : Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()

        activity!!.bigImg.setImageResource(R.drawable.tv)


    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(ViewModel :: class.java)

        seriesProgressBar.visibility = View.VISIBLE

        if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()) ){

            viewModel.discoverSeries()
        }else {
            Toast.makeText(requireContext(), "No Internet Connection" , Toast.LENGTH_LONG).show()
        }

        viewModel.discoverSeriesMutable.observe(this, Observer {

            discoverSeriesAdapter.submitList(it.results)

        })

        seriesProgressBar.visibility = View.GONE



        discoverSeriesAdapter.setOnItemClickListner {

            if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()) ){


                val intent = Intent(requireActivity() , SDetailsActivity::class.java)
                intent.putExtra("series" , it)

                startActivity(intent)
            } else {

                Toast.makeText(requireContext() , "No Internet Connection Found" , Toast.LENGTH_LONG).show()

            }


        }



        SsearchFab.setOnClickListener {



            if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()) ){

                if (searchSeries.text.isNotEmpty()){
                    Log.d("ahhhh", searchSeries.text.toString())

                    try {

                        val inputManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputManager.hideSoftInputFromWindow(view.windowToken, 0)

                        seriesProgressBar.visibility = View.VISIBLE


                        viewModel.searchSeries(searchSeries.text.toString() , activity!!.applicationContext)

                        viewModel.searchSeriesMutable.observe(viewLifecycleOwner, Observer {

                            discoverSeriesAdapter.submitList(it.results)

                        })

                        seriesProgressBar.visibility = View.GONE


                    } catch (e : Exception){
                        Toast.makeText(activity , "There's no Series with this name!" , Toast.LENGTH_LONG).show()
                    }


                } else {
                    seriesProgressBar.visibility = View.VISIBLE

                    viewModel.discoverSeries()

                    seriesProgressBar.visibility = View.GONE


                }
            } else {

                Toast.makeText(activity , "No Internet Connection" , Toast.LENGTH_LONG).show()

            }

        }


    }



    private val scrollListner = object : RecyclerView.OnScrollListener()    {


        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            super.onScrolled(recyclerView, dx, dy)

            val layout = recyclerView.layoutManager as GridLayoutManager

            val totalItem = layout.getItemCount()
            Log.d("kk" , totalItem.toString())

            val visibleItemCount = layout.getItemCount()
            Log.d("kk" , visibleItemCount.toString())

            val firstVisibleItem = layout.findFirstVisibleItemPosition()
            Log.d("kk" , firstVisibleItem.toString())

            val isNoLoadingAndNotLastPage = !isLoading && !isLastPage

            val isAtLastItem = firstVisibleItem + visibleItemCount >= totalItem

            val isNotAtBeginning = firstVisibleItem >= 0

            val isTotalMoreThanVisable = totalItem >= 20

            val shouldPaginate = isNoLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisable && isScrolling

            if (shouldPaginate && Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()) )
            {
                if (searchSeries.text.isNotEmpty()){

                    seriesProgressBar.visibility = View.VISIBLE

                    viewModel.searchSeries(searchSeries.text.toString() , activity!!)
                    isScrolling = false

                    seriesProgressBar.visibility = View.GONE


                } else {
                    seriesProgressBar.visibility = View.VISIBLE

                    viewModel.discoverSeries()
                    isScrolling = false

                    seriesProgressBar.visibility = View.GONE


                }

            } else if (shouldPaginate && !(Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()))){
                Toast.makeText(activity , "No Internet Connection" , Toast.LENGTH_LONG).show()
            }

        }
    }




    private fun setupRecyclerView(){
        discoverSeriesAdapter = DiscoverSeriesAdapter()
        layoutManager = GridLayoutManager(activity , 3)
        rvSeries.layoutManager = layoutManager
        rvSeries.adapter = discoverSeriesAdapter
        rvSeries.addOnScrollListener(scrollListner)
    }
    

}