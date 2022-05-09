package com.example.moviesland.db

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesland.Models.MovieModel.MovieModel
import com.example.moviesland.Models.MovieModel.Result
import com.example.moviesland.Models.SeriesModel.SeriesResult

@Database(entities = [Result:: class , SeriesResult::class] , version = 1 , exportSchema = false)
@TypeConverters(Converters :: class)
abstract class MovieDatabase : RoomDatabase() {


    abstract fun movieDao() : MovieDao
    abstract fun seriesDao() : SeriesDao

    companion object{

        private var instance : MovieDatabase? = null

        private val LOCK = Any()

        fun instanceOfDatabase(context : Context): MovieDatabase {
            synchronized(LOCK){

                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext , MovieDatabase::class.java , "MovieDatabase")
                        .fallbackToDestructiveMigration()
                        .build()


                }
                return instance!!
            }

        }

    }

}