package id.riverflows.moviicat.ui.home.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.data.entity.TvEntity
import id.riverflows.moviicat.databinding.FragmentGridOrListBinding
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.ui.decoration.SpaceItemDecoration
import id.riverflows.moviicat.ui.detail.movie.DetailMovieActivity
import id.riverflows.moviicat.ui.detail.tv.DetailTvActivity
import id.riverflows.moviicat.ui.home.HomeSharedViewModel
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GridFragment(private val type: String) : Fragment(){
    private var _binding: FragmentGridOrListBinding? = null
    private val binding
        get() = _binding as FragmentGridOrListBinding
    private lateinit var viewModel: HomeSharedViewModel
    private val movieAdapter = MoviePagedAdapter()
    private val tvAdapter = TvPagedAdapter()

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
        setupCallback()
        obtainViewModel()
        observeViewModel()
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(requireActivity(), factory)[HomeSharedViewModel::class.java]
    }

    private fun setupView(){
        with(binding.rvGridOrList){
            layoutManager = GridLayoutManager(context, UtilConstants.GRID_COUNT)
            addItemDecoration(SpaceItemDecoration(UtilConstants.SPACE_ITEM_DECORATION))
            adapter = if(type == TYPE_MOVIE) movieAdapter else tvAdapter
        }
    }

    private fun setupCallback(){
        if(type == TYPE_MOVIE){
            movieAdapter.setItemClickCallback(movieCallback)
        }else{
            tvAdapter.setItemClickCallback(tvCallback)
        }
    }

    private val movieCallback = object : MoviePagedAdapter.OnItemClickCallback {
        override fun onItemClicked(data: MovieEntity) {
            startActivity(
                Intent(context, DetailMovieActivity::class.java)
                    .putExtra(UtilConstants.EXTRA_MOVIE_ID, data.id)
            )
        }
    }

    private val tvCallback = object : TvPagedAdapter.OnItemClickCallback {
        override fun onItemClicked(data: TvEntity) {
            startActivity(
                Intent(context, DetailTvActivity::class.java)
                    .putExtra(UtilConstants.EXTRA_TV_ID, data.id)
            )
        }
    }

    private fun observeViewModel(){
        UtilIdlingResource.increment()
        lifecycleScope.launch(Dispatchers.Main){
            if(type == TYPE_MOVIE){
                viewModel.moviePaged.collectLatest { pagingData ->
                    movieAdapter.submitData(lifecycle, pagingData)
                }
            }else{
                viewModel.tvPaged.collectLatest { pagingData ->
                    tvAdapter.submitData(lifecycle, pagingData)
                }
            }
        }
        UtilIdlingResource.decrement()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TYPE_MOVIE = "movie_fragment"
        const val TYPE_TV = "tv_fragment"
        @JvmStatic
        fun newInstance(type: String) = GridFragment(type)
    }
}