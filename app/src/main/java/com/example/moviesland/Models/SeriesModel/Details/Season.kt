package com.example.moviesland.Models.SeriesModel.Details

data class Season(
    val air_date: Any,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String,
    val season_number: Int
)