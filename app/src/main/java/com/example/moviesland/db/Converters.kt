package com.example.moviesland.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun fromSourceToGson(genre_ids: List<Int>?) : String{
        return Gson().toJson(genre_ids)
    }

    @TypeConverter
    fun fromSourceToGson(str : String) : List<Int>{

        val type : Type = object : TypeToken<List<Int>>() {}.type

        return Gson().fromJson(str , type)
    }

    @TypeConverter
    fun toListString(str : List<String>) : String{

        return Gson().toJson(str)
    }


    @TypeConverter
    fun fromListStringToGson(str : String) : List<String>{

        val type : Type = object : TypeToken<List<String>>() {}.type

        return Gson().fromJson(str , type)
    }



}