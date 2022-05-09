package com.example.moviesland.Models.MovieModel

import androidx.lifecycle.MutableLiveData

data class MovieModel(
    val page: Int?,
    var results: MutableList<Result>?,
    val total_pages: Int?,
    val total_results: Int?
)