package com.imdb.movieList.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imdb.movieList.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    val movieLiveData get() = repository.movieDetails

    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(id)
        }
    }
}