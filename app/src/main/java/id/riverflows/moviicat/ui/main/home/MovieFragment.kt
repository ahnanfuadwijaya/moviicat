package id.riverflows.moviicat.ui.main.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.databinding.FragmentMovieBinding
import id.riverflows.moviicat.ui.decoration.SpaceItemDecoration
import id.riverflows.moviicat.ui.detail.movie.DetailMovieActivity
import id.riverflows.moviicat.ui.main.MovieViewModel
import id.riverflows.moviicat.util.UtilConstants

class MovieFragment : Fragment(), MovieListAdapter.OnItemClickCallback {
    private var _binding: FragmentMovieBinding? = null
    private val binding
        get() = _binding as FragmentMovieBinding
    private lateinit var viewModel: MovieViewModel
    private val rvAdapter = MovieListAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAdapter.setItemClickCallback(this)
        obtainViewModel()
        observeViewModel()
        populateRecyclerView()
        viewModel.getMovieList()
    }

    private fun obtainViewModel(){
        viewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]
    }

    private fun observeViewModel(){
        viewModel.movieList.observe(viewLifecycleOwner){
            bindRecyclerView(it)
        }
    }

    private fun populateRecyclerView(){
        with(binding.rvMovies){
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, UtilConstants.GRID_MOVIE_COUNT)
            addItemDecoration(SpaceItemDecoration(UtilConstants.SPACE_ITEM_DECORATION))
            adapter = rvAdapter
        }
    }

    private fun bindRecyclerView(list: List<MovieDetailEntity>){
        rvAdapter.setList(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClicked(data: MovieDetailEntity) {
        startActivity(Intent(context, DetailMovieActivity::class.java))
    }
}