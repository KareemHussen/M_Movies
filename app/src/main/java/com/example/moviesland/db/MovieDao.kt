package com.example.moviesland.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import com.example.moviesland.Models.MovieModel.MovieModel
import com.example.moviesland.Models.MovieModel.Result

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFav(movie: Result)

    @Query("select * from MovieTable")
    fun getFavMovies() : LiveData<List<Result>>

    @Delete
    suspend fun deleteMovie(movie: Result)

}