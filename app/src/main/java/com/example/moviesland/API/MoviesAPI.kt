package com.example.moviesland.API



import com.example.moviesland.Models.MovieModel.MovieModel
import com.example.moviesland.Models.SeriesModel.Details.SeriesDetails
import com.example.moviesland.Models.SeriesModel.SeriesModel
import com.example.moviesland.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {


    @GET("movie/popular")
    suspend fun homeMovie(
        @Query("api_key")  apikey : String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page : Int = 1
    ) : Response<MovieModel>


    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key")  api_key : String = API_KEY,
        @Query("language") language: String = "en",
        @Query("query") query : String,
        @Query("page") page : Int = 1,
        @Query("include_adult") include_adult : Boolean = false
    ) : Response<MovieModel>


    //https://api.themoviedb.org/3/discover/movie?api_key=1d403ee01b18cbb4eb2147e4faa8e94c&language=en-US&sort_by=release_date.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key")  api_key : String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("with_genres") genres : Int? = null,
        @Query("sort_by") sort_by : String = "popularity.desc",
        @Query("include_video") include_video : Boolean = false,
        @Query("page") page : Int = 1,
        @Query("with_watch_monetization_types") with_watch_monetization_types : String = "flatrate",
        @Query("include_adult") include_adult : Boolean = false
    ) : Response<MovieModel>


    //https://api.themoviedb.org/3/movie/19995/similar?api_key=1d3ee01b18cbb4eb2147e4faa8e94c&language=en-US&page=1


        @GET("movie/{id}/similar")
        suspend fun getSimilar(
            @Path("id") id: Int,
            @Query("api_key") api_key : String = API_KEY,
            @Query("language") language : String = "en-US",
            @Query("page") page : Int = 1
        ) : Response<MovieModel>

        //https://api.themoviedb.org/3/tv/popular?api_key=1d403ee01b18cbb4eb2147e4faa8e94c&language=en-US&page=1

    @GET("tv/popular")
    suspend fun discoverSeries(
        @Query("api_key")  api_key : String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page : Int = 1,
    ) : Response<SeriesModel>

    //https://api.themoviedb.org/3/search/tv?api_key=1d403ee01b18cbb4eb2147e4faa8e94c&language=en-US&page=1&query=john&include_adult=false

    @GET("search/tv")
    suspend fun searchSeries(
        @Query("api_key")  api_key : String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page : Int = 1,
        @Query("query") query : String,
        @Query("include_adult") include_adult : Boolean = false
    ) : Response<SeriesModel>


    //https://api.themoviedb.org/3/tv/79168/similar?api_key=1d403ee01b18cbb4eb2147e4faa8e94c&language=en-US&page=1
    @GET("tv/{id}/similar")
    suspend fun getSimilarSeries(
        @Path("id") id: Int,
        @Query("api_key") api_key : String = API_KEY,
        @Query("language") language : String = "en-US",
        @Query("page") page : Int = 1
    ) : Response<SeriesModel>


    //https://api.themoviedb.org/3/tv/60574?api_key=1d403ee01b18cbb4eb2147e4faa8e94c&language=en-US
    @GET("tv/{id}")
    suspend fun getSeriesDetails(
        @Path("id") id: Int,
        @Query("api_key") api_key : String = API_KEY,
        @Query("language") language : String = "en-US",
    ) : Response<SeriesDetails>

    //https://api.themoviedb.org/3/tv/popular?api_key=1d403ee01b18cbb4eb2147e4faa8e94c&language=en-US&page=1
    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") api_key : String = API_KEY,
        @Query("language") language : String = "en-US",
        @Query("page") page : Int = 1
    ) : Response<SeriesModel>

}