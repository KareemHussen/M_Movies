package com.example.moviesland.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviesland.Models.MovieModel.Result
import com.example.moviesland.Models.SeriesModel.SeriesResult


@Dao
interface SeriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(series: SeriesResult)

    @Query("select * from SeriesTable")
    fun getAllSeries() : LiveData<List<SeriesResult>>

    @Delete
    suspend fun deleteSeries(movie: SeriesResult)
}