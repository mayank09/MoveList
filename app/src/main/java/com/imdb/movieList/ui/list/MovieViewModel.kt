package com.imdb.movieList.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.imdb.movieList.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(repository: MovieRepository): ViewModel() {
  val movieList = repository.getMovieList().cachedIn(viewModelScope)
}