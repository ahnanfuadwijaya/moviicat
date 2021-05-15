package id.riverflows.moviicat.ui.home.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.databinding.FragmentGridOrListBinding
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.ui.detail.movie.DetailMovieActivity
import id.riverflows.moviicat.util.UtilConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


class SearchMovieFragment : Fragment(), SearchMoviePagedAdapter.OnItemClickCallback {
    private var _binding: FragmentGridOrListBinding? = null
    private val binding
        get() = _binding as FragmentGridOrListBinding
    private val movieAdapter = SearchMoviePagedAdapter()
    private val viewModel by viewModels<SearchViewModel> { ViewModelFactory.getInstance() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGridOrListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView(){
        movieAdapter.setItemClickCallback(this)
        with(binding.rvGridOrList){
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    fun searchMovie(query: String){
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.getMovieSearchResultPaged(query).collectLatest {
                movieAdapter.submitData(it)
            }
        }
        Timber.d(query)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClicked(data: MovieEntity) {
        startActivity(Intent(context, DetailMovieActivity::class.java).apply {
            putExtra(UtilConstants.EXTRA_MOVIE_ID, data.id)
        })
    }
}