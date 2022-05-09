package com.example.moviesland.Models.MovieModel

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "MovieTable")
data class Result(
    var isFav : Boolean? = false,
    var isAnime : Boolean? = false,
    var isMovie : Boolean? = false,
    var isHomeMovie : Boolean? = false,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val original_language: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Int?, // 3
    val title: String?, // 1
    val original_title: String?,
    val release_date: String?, // 2
    val adult: Boolean?, // 4
    val video: Boolean?,
) : Parcelable