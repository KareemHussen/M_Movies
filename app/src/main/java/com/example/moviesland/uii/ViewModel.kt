package com.example.moviesland.uii


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesland.API.RetrofitMovie
import com.example.moviesland.Models.MovieModel.MovieModel
import com.example.moviesland.Models.MovieModel.Result
import com.example.moviesland.Models.SeriesModel.Details.SeriesDetails
import com.example.moviesland.Models.SeriesModel.SeriesModel
import com.example.moviesland.db.MovieDatabase
import kotlinx.coroutines.launch
import retrofit2.Response


class ViewModel : ViewModel() {

    var HomeMoviesMutable: MutableLiveData<MovieModel> = MutableLiveData()
    var HomeSeriesMutable: MutableLiveData<SeriesModel> = MutableLiveData()

    var discoverMutable: MutableLiveData<MovieModel> = MutableLiveData()
    var movieModel: MovieModel? = null
    var discoverPage = 1


    var discoverResponse : MovieModel? = null

    var similarMutable: MutableLiveData<MovieModel> = MutableLiveData()

    var searchMutable : MutableLiveData<MovieModel> = MutableLiveData()
    var searchResponse : MovieModel? = null
    var searchPage = 1

    var first : String? = null
    var second : String? = null

    var kidsDiscoverMutable: MutableLiveData<MovieModel> = MutableLiveData()
    var kidsDiscoverResponse : MovieModel? = null
    var kidsPage = 1

    var kidsSearchMutable : MutableLiveData<MovieModel> = MutableLiveData()


    var discoverSeriesMutable : MutableLiveData<SeriesModel> = MutableLiveData()
    var discoverSeriesResponse : SeriesModel? = null
    var discoverSeriesPage = 1

    var searchSeriesMutable : MutableLiveData<SeriesModel> = MutableLiveData()
    var searchSeriesResponse : SeriesModel? = null
    var searchSeriesPage = 1

    var sfirst : String? = null

    var similarSeriesMutable: MutableLiveData<SeriesModel> = MutableLiveData()

    var SeriesDetailsMutable: MutableLiveData<SeriesDetails> = MutableLiveData()


    fun homeMovies() {


        viewModelScope.launch {


            Log.d("sui" , "homee" )

            var response = RetrofitMovie.api.homeMovie()


            if (response.isSuccessful) {
                Log.d("sui" , "home" )

                HomeMoviesMutable.value = response.body()
            }

        }
    }


    fun homeSeries() {


        viewModelScope.launch {


            Log.d("sui" , "homee" )

            var response = RetrofitMovie.api.getPopularSeries()


            if (response.isSuccessful) {
                Log.d("sui" , "home" )

                HomeSeriesMutable.value = response.body()
            }

        }
    }


    fun discoverMovies(context: Context) {

        viewModelScope.launch {

            var response = RetrofitMovie.api.discoverMovies(page = discoverPage++)

            if (response.isSuccessful) {

                if (discoverResponse == null){

                    discoverResponse = response.body()!!


                } else {

                    val oldMovies = discoverResponse!!.results
                    val newMovies = response.body()!!.results

                    oldMovies!!.addAll(newMovies!!)

                }



                discoverMutable.value = discoverResponse


            }

        }

    }


    fun searchMovies(title : String , context: Context) {

        viewModelScope.launch {
            val response : Response<MovieModel>
            if (first == null){
                first = title
                response = RetrofitMovie.api.searchMovie(query = first!!, page = searchPage)
            } else {

                if (first == title){

                    searchPage++

                    response = RetrofitMovie.api.searchMovie(query = first!!, page = searchPage)
                } else {

                    first = title

                    searchPage = 1

                    response = RetrofitMovie.api.searchMovie(query = first!!, page = searchPage)
                    searchResponse = null
                }

            }




            if (response.isSuccessful) {

                if (response.body()!!.total_results!! > 0){

                    if (searchResponse == null){

                        searchResponse = response.body()!!

                    } else {

                        val oldMovies = searchResponse!!.results
                        val newMovies = response.body()!!.results

                        oldMovies!!.addAll(newMovies!!)

                    }



                    searchMutable.value = searchResponse

                } else {

                    Toast.makeText(context , "There's No Movie With This Name!" , Toast.LENGTH_LONG).show()

                }
            }
        }
    }


    fun getSimilar(id: Int) {

        viewModelScope.launch {

            val response = RetrofitMovie.api.getSimilar(id)

            if (response.isSuccessful) {


                similarMutable.value = response.body()


            }

        }

    }


    fun kidsDiscoverMovies() {

        viewModelScope.launch {

            var response = RetrofitMovie.api.discoverMovies(genres = 16 , page = kidsPage++)

            if (response.isSuccessful) {

                if (kidsDiscoverResponse == null){

                    kidsDiscoverResponse = response.body()!!


                } else {

                    val oldMovies = kidsDiscoverResponse!!.results
                    val newMovies = response.body()!!.results

                    oldMovies!!.addAll(newMovies!!)

                }



                kidsDiscoverMutable.value = kidsDiscoverResponse


            }

        }

    }


    fun kidsSearchMovies(title : String , context: Context) {

        viewModelScope.launch {

            val response : Response<MovieModel>

            if (second == null){
                second = title
                response =  RetrofitMovie.api.searchMovie(query = title)

            } else {

                if (second == title){

                    kidsPage++
                    response =  RetrofitMovie.api.searchMovie(query = title , page = kidsPage)
                } else {

                    kidsPage = 1

                    second = title

                    response =  RetrofitMovie.api.searchMovie(query = title)
                }
            }

            if (response.isSuccessful) {

                if (response.body()!!.total_results!! > 0){

                    kidsSearchMutable.value = response.body()

                } else {

                    Toast.makeText(context , "There's No Movie With This Name!" , Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    fun upsert(context: Context , movie: Result){

        viewModelScope.launch {

            val database = MovieDatabase.instanceOfDatabase(context)

            movie.isFav = true
            database.movieDao().upsertFav(movie)



        }

    }


    fun getFavMovies(context: Context) = MovieDatabase.instanceOfDatabase(context).movieDao().getFavMovies()





    fun deleteFromFav(context: Context, movie: Result){

        viewModelScope.launch {

            val database = MovieDatabase.instanceOfDatabase(context)

            database.movieDao().deleteMovie(movie)


        }

    }

    fun discoverSeries() {
        viewModelScope.launch {

            var response = RetrofitMovie.api.discoverSeries(page = discoverSeriesPage)

            Log.d("pppp", response.body()!!.results.toString())

            if (response.isSuccessful) {
                discoverSeriesPage++
                Log.d("pppp", response.body()!!.results.toString())
                if (discoverSeriesResponse == null){

                    discoverSeriesResponse = response.body()!!

                } else {

                    val oldSeries = discoverSeriesResponse!!.results
                    val newSeries = response.body()!!.results

                    oldSeries!!.addAll(newSeries!!)

                }



                discoverSeriesMutable.value = discoverSeriesResponse


            }

        }

    }


    fun searchSeries(title : String , context: Context) {

        viewModelScope.launch {

            Log.d("ahhhh", "response.body()!!.results.toString()")

            var response : Response<SeriesModel>

            if (sfirst == null){
                sfirst = title

                response = RetrofitMovie.api.searchSeries(query = sfirst!!, page = searchSeriesPage)
            } else {

                if (sfirst == title){

                    searchSeriesPage++

                    response = RetrofitMovie.api.searchSeries(query = sfirst!!, page = searchSeriesPage)
                } else {

                    sfirst = title

                    searchSeriesPage = 1

                    response = RetrofitMovie.api.searchSeries(query = sfirst!!, page = searchSeriesPage)
                    searchSeriesResponse = null
                }

            }



            if (response.isSuccessful) {

                if (response.body()!!.total_results!! > 0){

                    if (searchSeriesResponse == null){

                        searchSeriesResponse = response.body()

                    } else {

                        val oldSeries = searchSeriesResponse!!.results
                        val newSeries = response.body()!!.results

                        oldSeries!!.addAll(newSeries!!)


                    }

                    searchSeriesMutable.value = searchSeriesResponse





                } else {

                    Toast.makeText(context , "There's No Series With This Name!" , Toast.LENGTH_LONG).show()

                }
            }
        }
    }


    fun getSimilarSeries(id: Int) {

        viewModelScope.launch {

            val response = RetrofitMovie.api.getSimilarSeries(id)

            if (response.isSuccessful) {


                similarSeriesMutable.value = response.body()


            }

        }

    }

    fun getSeriesDetails(id : Int){
        viewModelScope.launch {

            val response = RetrofitMovie.api.getSeriesDetails(id)

            if (response.isSuccessful){

                Log.d("ya3m" , response.body()!!.seasons.size.toString())
                SeriesDetailsMutable.value = response.body()

            }

        }
    }








}