package com.example.moviesland.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesland.Models.MovieModel.Result
import com.example.moviesland.R
import kotlinx.android.synthetic.main.home_movie_item.view.*
import kotlinx.android.synthetic.main.movie_item.view.*

class KidsAdapter() :  ListAdapter<Result, RecyclerView.ViewHolder>(diff()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            return ViewHolderr(
                LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
            ) // we make it ready and send it to ViewHolder down -> Access CardView





    }



    class ViewHolderr(view: View) : RecyclerView.ViewHolder(view) {}

    class LoadingHolder(view: View) : RecyclerView.ViewHolder(view) {}


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val current = getItem(position)
        if (current.adult == false){

            holder.itemView.apply {

                if (current.backdrop_path == null){
                    movieImg.setImageResource(R.drawable.notimgicon)
                } else {
                    Glide.with(this).load("https://image.tmdb.org/t/p/original${current.poster_path}").into(movieImg)
                }
                movieTitle.text = current.title

                setOnClickListener {
                    onItemClickListner?.let {
                            k -> k(current)
                    }
                }
            }

        }


    }




    private var  onItemClickListner : ((Result) -> Unit)? = null // Third -> onItemClickListner Carry My Fun From Activity to here


    fun setOnItemClickListner(listner: (Result) -> Unit){ // First -> from activiy to here
        onItemClickListner = listner // Second -> the onItemClickListner take my fun from activity send to to above

    }

    class diff : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }




    }

}