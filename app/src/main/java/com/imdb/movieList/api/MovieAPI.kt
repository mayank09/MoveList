package com.imdb.movieList.api

import com.imdb.movieList.model.MovieDetailsResponse
import com.imdb.movieList.model.MovieListResponse
import com.imdb.movieList.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val DISCOVER = "discover/movie"
private const val MOVIE = "movie/{movie_id}"

interface MovieAPI {

    @GET(DISCOVER)
    suspend fun getMovieList(@Query("page") page: Int, @Query("api_key") key: String = API_KEY): MovieListResponse

    @GET(MOVIE)
    suspend fun getMovieDetails(@Path("movie_id") id: Int, @Query("api_key") key: String = API_KEY): Response<MovieDetailsResponse>
}