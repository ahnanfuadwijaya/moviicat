package id.riverflows.moviicat.ui.home.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import id.riverflows.moviicat.data.entity.TvEntity
import id.riverflows.moviicat.databinding.FragmentGridOrListBinding
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.ui.detail.tv.DetailTvActivity
import id.riverflows.moviicat.ui.home.HomeSharedViewModel
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchTvFragment : Fragment(), SearchTvPagedAdapter.OnItemClickCallback {
    private var _binding: FragmentGridOrListBinding? = null
    private val binding
        get() = _binding as FragmentGridOrListBinding
    private val tvAdapter = SearchTvPagedAdapter()
    private lateinit var viewModel: HomeSharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGridOrListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        obtainViewModel()
    }

    private fun setupView(){
        tvAdapter.setItemClickCallback(this)
        with(binding.rvGridOrList){
            layoutManager = LinearLayoutManager(context)
            adapter = tvAdapter
        }
    }
    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(viewModelStore, factory)[HomeSharedViewModel::class.java]
    }

    fun searchTv(query: String){
        UtilIdlingResource.increment()
        lifecycleScope.launch(Dispatchers.Main){
            viewModel.tvSearchResultPaged(query).collectLatest {
                tvAdapter.submitData(lifecycle, it)
            }
        }
        UtilIdlingResource.decrement()
    }

    override fun onItemClicked(data: TvEntity) {
        startActivity(Intent(context, DetailTvActivity::class.java).apply {
            putExtra(UtilConstants.EXTRA_TV_ID, data.id)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}