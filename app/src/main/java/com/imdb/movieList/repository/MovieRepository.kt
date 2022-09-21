package com.imdb.movieList.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.imdb.movieList.api.MovieAPI
import com.imdb.movieList.model.MovieDetailsResponse
import com.imdb.movieList.paging.MoviePagingSource
import com.imdb.movieList.utils.MAX_SIZE
import com.imdb.movieList.utils.NETWORK_PAGE_SIZE
import com.imdb.movieList.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class MovieRepository@Inject constructor(private val api: MovieAPI) {

    private val _movieDetails = MutableLiveData<NetworkResult<MovieDetailsResponse>>()
    val movieDetails: LiveData<NetworkResult<MovieDetailsResponse>> = _movieDetails

    fun getMovieList() = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, maxSize = MAX_SIZE),
        pagingSourceFactory = {MoviePagingSource(api)}
    ).liveData

    suspend fun getMovieDetails(imdbId: Int) {
        _movieDetails.postValue(NetworkResult.Loading())
        val response = api.getMovieDetails(imdbId)
        if (response.isSuccessful && response.body() != null) {
            _movieDetails.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _movieDetails.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _movieDetails.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}