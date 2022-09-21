package com.imdb.movieList.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imdb.movieList.databinding.FragmentMovieListBinding
import com.imdb.movieList.paging.LoadAdapter
import com.imdb.movieList.paging.MoviePagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val moviesViewModel by viewModels<MovieViewModel>()
    private lateinit var listAdapter: MoviePagingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel.movieList
        binding.movieList.setUpList()
        observeMovieList()
    }

    private fun onMovieItemClicked(id: Int){
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(id)
        findNavController().navigate(action)
    }

    private fun RecyclerView.setUpList(){
        listAdapter = MoviePagingAdapter(::onMovieItemClicked)
        listAdapter.addLoadStateListener { loadState ->
            binding.movieList.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.shimmerViewContainer.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryUi.root.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }

        binding.retryUi.btnRetry.setOnClickListener {
            listAdapter.retry()
        }

        layoutManager = LinearLayoutManager(activity)
        setHasFixedSize(true)
        adapter = listAdapter.withLoadStateHeaderAndFooter(
            header = LoadAdapter{listAdapter.retry()},
            footer = LoadAdapter{listAdapter.retry()}
        )
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            //handle error
            //Toast.makeText(requireActivity(), "${it.error}", Toast.LENGTH_LONG).show()
        }
    }

    private fun observeMovieList(){
        moviesViewModel.movieList.observe(viewLifecycleOwner){
            listAdapter.submitData(lifecycle,it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}