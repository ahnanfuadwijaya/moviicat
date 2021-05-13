package id.riverflows.moviicat.ui.home.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.databinding.FragmentFavoriteBinding
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.ui.detail.movie.DetailMovieActivity
import id.riverflows.moviicat.ui.detail.tv.DetailTvActivity
import id.riverflows.moviicat.util.UtilConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class FavoriteFragment : Fragment(), FavoritePagedListAdapter.OnItemClickCallback {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding
        get() = _binding as FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private val rvAdapter = FavoritePagedListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        obtainViewModel()
        observeViewModel()
        Timber.d("onViewCreated")
    }

    private fun setupRecyclerView(){
        rvAdapter.setItemClickCallback(this)
        with(binding.rvFavorites){
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(viewModelStore, factory)[FavoriteViewModel::class.java]
    }

    private fun observeViewModel(){
        viewModel.getFavoritePagedList().observe(viewLifecycleOwner){
            lifecycleScope.launch(Dispatchers.IO){
                rvAdapter.submitData(it)
            }
            Timber.d(it.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClicked(data: FavoriteEntity) {
        when(data.type){
            UtilConstants.TYPE_MOVIE -> startActivity(Intent(context, DetailMovieActivity::class.java).apply {
                putExtra(UtilConstants.EXTRA_MOVIE_ID, data.id)
            })
            else -> startActivity(Intent(context, DetailTvActivity::class.java).apply {
                putExtra(UtilConstants.EXTRA_TV_ID, data.id)
            })
        }
    }
}