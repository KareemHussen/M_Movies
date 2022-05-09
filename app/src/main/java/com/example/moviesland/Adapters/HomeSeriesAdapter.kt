package com.example.moviesland.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesland.Models.SeriesModel.SeriesResult
import com.example.moviesland.R
import kotlinx.android.synthetic.main.home_movie_item.view.*

class HomeSeriesAdapter() : ListAdapter<SeriesResult, HomeSeriesAdapter.ViewHolder>(diff()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_movie_item, parent, false)
        ) // we make it ready and send it to ViewHolder down -> Access CardView

    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) { // I Made It MySelf
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val current = getItem(position)

            holder.itemView.apply {
                if (current.backdrop_path == null){
                    image.setImageResource(R.drawable.notimgicon)
                }else {
                    Glide.with(this).load("https://image.tmdb.org/t/p/original${current.poster_path}").into(image)
                }

                setOnClickListener {
                    onItemClickListner?.let {
                            k -> k(current)
                    }
                }
            }





    }


    private var  onItemClickListner : ((SeriesResult) -> Unit)? = null // Third -> onItemClickListner Carry My Fun From Activity to here


    fun setOnItemClickListner(listner: (SeriesResult) -> Unit){ // First -> from activiy to here
        onItemClickListner = listner // Second -> the onItemClickListner take my fun from activity send to to above

    }


    class diff : DiffUtil.ItemCallback<SeriesResult>() {
        override fun areItemsTheSame(oldItem: SeriesResult, newItem: SeriesResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SeriesResult, newItem: SeriesResult): Boolean {
            return oldItem == newItem
        }




    }



}
