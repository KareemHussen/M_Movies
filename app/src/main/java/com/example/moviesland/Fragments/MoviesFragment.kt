package com.example.moviesland.Fragments

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
import com.example.moviesland.Adapters.DiscoverMovieAdapter
import com.example.moviesland.R
import com.example.moviesland.uii.DetailsActiviy
import com.example.moviesland.uii.ViewModel
import com.example.moviesland.util.Constants
import com.example.moviesland.util.Constants.Companion.Net
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movies_layout.*
import java.lang.Exception
import java.net.InetAddress
import java.util.concurrent.TimeUnit


class MoviesFragment : Fragment(R.layout.movies_layout) {

    lateinit var viewModel : ViewModel
    lateinit var discoverMovieAdapter: DiscoverMovieAdapter
    lateinit var layoutManager: GridLayoutManager
    var isLoading = false
    var isScrolling = false
    var isLastPage = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()

        activity!!.bigImg.setImageResource(R.drawable.movies)

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModel :: class.java)

        setupRecyclerView()



        moviesProgressBar.visibility = View.VISIBLE

            if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()) ){
                Log.d("neeet" , "Yup")
                viewModel.discoverMovies(requireContext())



                searchFab.setOnClickListener {



                    if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()) ){

                        if (searchMovie.text.isNotEmpty()) {
0

                            try {

                                val inputManager =
                                    context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                inputManager.hideSoftInputFromWindow(view.windowToken, 0)

                                moviesProgressBar.visibility = View.VISIBLE

                                viewModel.searchMovies(
                                    searchMovie.text.toString(),
                                    activity!!.applicationContext
                                )
                                viewModel.searchMutable.observe(this, Observer {

                                    discoverMovieAdapter.submitList(it.results)

                                    moviesProgressBar.visibility = View.GONE


                                })

                            } catch (e: Exception) {
                                Toast.makeText(
                                    activity,
                                    "There's no movie with this name!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }


                        } else {
                            moviesProgressBar.visibility = View.VISIBLE

                            viewModel.discoverMovies(requireContext())

                            moviesProgressBar.visibility = View.GONE


                             }

                    } else {

                        Toast.makeText(requireContext() , "No Internet Connection Found" , Toast.LENGTH_LONG).show()

                        }

                    }






            } else {


                Toast.makeText(requireContext() , "No Internet Connection Found" , Toast.LENGTH_LONG).show()


            }


        discoverMovieAdapter.setOnItemClickListner {

            if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()) ){


                val intent = Intent(activity, DetailsActiviy::class.java)
                intent.putExtra("movie", it)

                startActivity(intent)
            }else {

                Toast.makeText(requireContext() , "No Internet Connection Found" , Toast.LENGTH_LONG).show()

            }


        }

                moviesProgressBar.visibility = View.VISIBLE


                viewModel.discoverMutable.observe(this, Observer {

                    discoverMovieAdapter.submitList(it.results)

                })

                moviesProgressBar.visibility = View.GONE









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
                if (searchMovie.text.isNotEmpty()){
                    moviesProgressBar.visibility = View.VISIBLE

                    viewModel.searchMovies(searchMovie.text.toString() , activity!!)

                    isScrolling = false

                    moviesProgressBar.visibility = View.GONE


                } else {
                    moviesProgressBar.visibility = View.VISIBLE

                    viewModel.discoverMovies(requireContext())
                    isScrolling = false

                    moviesProgressBar.visibility = View.GONE


                }

            } else if (shouldPaginate && !(Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()))){

                Toast.makeText(requireContext() , "No Internet Connection" , Toast.LENGTH_LONG).show()
            }

        }

    }




    private fun setupRecyclerView(){
        discoverMovieAdapter = DiscoverMovieAdapter()
        layoutManager = GridLayoutManager(activity , 3)
        rvMovies.layoutManager = layoutManager
        rvMovies.adapter = discoverMovieAdapter
        rvMovies.addOnScrollListener(scrollListner)
    }

    


}