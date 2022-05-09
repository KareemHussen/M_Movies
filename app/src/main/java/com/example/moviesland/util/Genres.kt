package com.example.moviesland.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Genres {

        companion object {
                fun getGenres(id: Int): String {
                    when (id) {
                        28 -> return "Action"
                        10759 -> return  "Adventure"
                        37 -> return "Western"
                        12 -> return "Adventure"
                        16 -> return "Animation"
                        10762 -> return  "Kids"
                        35 -> return "Comedy"
                        80 -> return "Crime"
                        99 -> return "Documentary"
                        18 -> return "Drama"
                        10751 -> return "Family"
                        10768 -> return  "War & Politics"
                        10767 -> return  "Talk"
                        10766 -> return  "Soap"
                        10765 -> return  "Fantasy"
                        14 -> return "Fantasy"
                        36 -> return "History"
                        27 -> return "Horror"
                        10402 -> return "Music"
                        10764 -> return "Reality"
                        10763 -> return  "News"
                        10749 -> return "Romance"
                        878 -> return "ScienceFiction"
                        10770 -> return "TVMovie"
                        53 -> return "Thriller"
                        10752 -> return "War"
                        else -> return "Mystery" // 9648


                    }

                }

        }


}

