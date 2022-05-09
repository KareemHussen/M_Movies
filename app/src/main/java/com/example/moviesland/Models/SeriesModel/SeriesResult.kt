package com.example.moviesland.Models.SeriesModel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "SeriesTable")
data class SeriesResult(
    val typeId : Int,
    val backdrop_path: String?, //1
    val genre_ids: List<Int>?, // 2
    @PrimaryKey(autoGenerate = true)
    val id: Int?, // 3
    val original_language: String?, // 5
    val overview: String?,  // 7
    val popularity: Double?, // 8
    val poster_path: String?, // 9
    val vote_average: Double?,
    val vote_count: Int?, // 11
    val name: String?,
    val original_name: String?, // 6
    val first_air_date: String?,// 10
    val origin_country: List<String>?, // 4
) : Parcelable