package com.example.moviesland.Fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesland.Adapters.KidsAdapter
import com.example.moviesland.MainActivity
import com.example.moviesland.Models.MovieModel.Result
import com.example.moviesland.R
import com.example.moviesland.uii.DetailsActiviy
import com.example.moviesland.uii.ViewModel
import com.example.moviesland.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.kids_layout.*
import java.lang.Exception
import java.net.InetAddress
import java.util.concurrent.TimeUnit


class KidsFragment : Fragment(R.layout.kids_layout) {

    lateinit var viewModel: ViewModel
    lateinit var kidsDiscoverMovieAdapter: KidsAdapter
    lateinit var layoutManager: GridLayoutManager
    var isLoading = false
    var isScrolling = false
    var isLastPage = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        requireActivity().bigImg.setImageResource(R.drawable.cartoon)


    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        kidsProgressBar.visibility = View.VISIBLE

        if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext())){

            viewModel.kidsDiscoverMovies()

        } else {

            Toast.makeText(activity , "No Internet Connection" , Toast.LENGTH_LONG).show()

        }

        viewModel.kidsDiscoverMutable.observe(viewLifecycleOwner, Observer {

            Log.d("kimo", it.results?.get(0)!!.id.toString())
            kidsDiscoverMovieAdapter.submitList(it.results)

        })

        kidsProgressBar.visibility = View.GONE


        kidsDiscoverMovieAdapter.setOnItemClickListner {

            if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext())){

                var intent = Intent(activity, DetailsActiviy::class.java)
                intent.putExtra("movie", it)

                startActivity(intent)
            } else {
                Toast.makeText(requireContext() , "No Internet Connection Found" , Toast.LENGTH_LONG).show()

            }


        }

        kidsSearchFab.setOnClickListener {



            if (Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext())){


                if (kidsSearchMovie.text.isNotEmpty()) {
                    try {

                        val inputManager =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputManager.hideSoftInputFromWindow(view.windowToken, 0)

                        kidsProgressBar.visibility = View.VISIBLE

                        viewModel.kidsSearchMovies(
                            kidsSearchMovie.text.toString(),
                            requireActivity().applicationContext
                        )

                        viewModel.kidsSearchMutable.observe(viewLifecycleOwner, Observer {
                            Log.d("kimo", it.toString())
                            Log.d("kimo", it.results.toString())
                            Log.d("kimo", it.results!!.size.toString())

                            val listforkids = mutableListOf<Result>()


                            for (i in it.results!!) {

                                if (i.genre_ids!!.contains(16)) {
                                    listforkids.add(i)
                                }
                            }
                            kidsDiscoverMovieAdapter.submitList(listforkids)

                            kidsProgressBar.visibility = View.GONE


                        })

                    } catch (e: Exception) {
                        Toast.makeText(activity, "There's no movie with this name!", Toast.LENGTH_LONG)
                            .show()
                    }


                } else {
                    kidsProgressBar.visibility = View.VISIBLE

                    viewModel.kidsDiscoverMovies()

                    kidsProgressBar.visibility = View.GONE
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
                if (kidsSearchMovie.text.isNotEmpty()){

                    kidsProgressBar.visibility = View.VISIBLE


                    viewModel.kidsSearchMovies(kidsSearchMovie.text.toString() , activity!!)
                    isScrolling = false

                    kidsProgressBar.visibility = View.GONE

                } else {
                    kidsProgressBar.visibility = View.VISIBLE

                    viewModel.kidsDiscoverMovies()
                    isScrolling = false

                    kidsProgressBar.visibility = View.GONE


                }

            } else if (shouldPaginate && !(Constants.isInternetAvailable(requireContext()) && Constants.isNetworkAvailable(requireContext()))){

                Toast.makeText(activity , "No Internet Connection" , Toast.LENGTH_LONG).show()

            }

        }
    }



    private fun setupRecyclerView(){
            kidsDiscoverMovieAdapter = KidsAdapter()
            layoutManager = GridLayoutManager(MainActivity() ,3)
            kidsRvMovies.layoutManager = layoutManager
            kidsRvMovies.adapter = kidsDiscoverMovieAdapter
            kidsRvMovies.addOnScrollListener(scrollListner)

    }


}