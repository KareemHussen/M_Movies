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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesland.Adapters.FavAdapter
import com.example.moviesland.MainActivity
import com.example.moviesland.R
import com.example.moviesland.uii.DetailsActiviy
import com.example.moviesland.uii.ViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.favourite_layout.*
import java.lang.Exception
import java.net.InetAddress
import java.util.concurrent.TimeUnit

class FavouriteFragment : Fragment(R.layout.favourite_layout) {

    lateinit var viewModel : ViewModel
    lateinit var favAdapter : FavAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(ViewModel :: class.java)

        viewModel.getFavMovies(requireActivity().applicationContext).observe(viewLifecycleOwner , Observer {

            Log.d("ahh" , it.toString())
            favAdapter.submitList(it)

        })




        favAdapter.setOnItemClickListner {

            if (checkConnection()){

                val intent = Intent(activity , DetailsActiviy::class.java)
                intent.putExtra("movie" , it)

                startActivity(intent)
            } else {

                Toast.makeText(requireContext() , "No Internet Connection Found" , Toast.LENGTH_LONG).show()

            }

        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.layoutPosition

                val movie = favAdapter.currentList.get(position)

                viewModel.deleteFromFav(requireContext(), movie)

                Snackbar.make(view, "Successfully Deleted Movie" , Snackbar.LENGTH_LONG).apply {
                    setAction("undo") {
                        viewModel.upsert(context, movie)
                    }.show()
                }

            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvFavourite)


    }

    private fun setupRecyclerView(){
        favAdapter = FavAdapter()
        rvFavourite.layoutManager = LinearLayoutManager(MainActivity() , LinearLayoutManager.VERTICAL , false)
        rvFavourite.adapter = favAdapter
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkConnection(): Boolean {
        var result = false
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
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
            Runtime.getRuntime().exec(command).waitFor(1 , TimeUnit.MILLISECONDS) == true
        } catch (e: Exception) {
            false
        }
    }

}