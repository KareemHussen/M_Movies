package com.example.moviesland.Models.SeriesModel

data class SeriesModel(
    val page: Int,
    val results: MutableList<SeriesResult>,
    val total_pages: Int,
    val total_results: Int
)