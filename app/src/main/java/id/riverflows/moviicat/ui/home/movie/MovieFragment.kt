package id.riverflows.moviicat.ui.home.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.databinding.FragmentMovieBinding
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.ui.adapter.MovieListAdapter
import id.riverflows.moviicat.ui.decoration.SpaceItemDecoration
import id.riverflows.moviicat.ui.detail.movie.DetailMovieActivity
import id.riverflows.moviicat.util.UtilIdlingResource
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilErrorMessage
import id.riverflows.moviicat.util.UtilSnackBar

class MovieFragment : Fragment(), MovieListAdapter.OnItemClickCallback {
    private var _binding: FragmentMovieBinding? = null
    private val binding
        get() = _binding as FragmentMovieBinding
    private lateinit var viewModel: MovieViewModel
    private val rvAdapter = MovieListAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindInterface()
        obtainViewModel()
        observeViewModel()
        setupRecyclerView()
        requestData()
    }

    private fun requestData(){
        viewModel.getMovieList()
        setLoadingState(true)
        UtilIdlingResource.increment()
    }

    private fun bindInterface(){
        rvAdapter.setItemClickCallback(this)
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]
    }

    private fun observeViewModel(){
        viewModel.movieList.observe(viewLifecycleOwner){
            setLoadingState(false)
            UtilIdlingResource.decrement()
            when(it){
                is Resource.Success -> {
                    bindRecyclerView(it.value.data)
                }
                is Resource.Failure ->{
                    val message = UtilErrorMessage.getErrorMessage(requireContext(), it.code)
                    UtilSnackBar.showIndeterminate(binding.root, message)
                }
            }
        }
    }

    private fun setupRecyclerView(){
        with(binding.rvMovies){
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, UtilConstants.GRID_MOVIE_COUNT)
            addItemDecoration(SpaceItemDecoration(UtilConstants.SPACE_ITEM_DECORATION))
            adapter = rvAdapter
        }
    }

    private fun bindRecyclerView(list: List<MovieEntity>){
        rvAdapter.setList(list)
    }

    override fun onItemClicked(data: MovieEntity) {
        startActivity(Intent(context, DetailMovieActivity::class.java).putExtra(UtilConstants.EXTRA_MOVIE_ID, data.id))
    }

    private fun setLoadingState(isLoading: Boolean){
        with(binding.shimmerContainer){
            visibility = if(isLoading){
                startShimmerAnimation()
                View.VISIBLE
            }else{
                stopShimmerAnimation()
                View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}