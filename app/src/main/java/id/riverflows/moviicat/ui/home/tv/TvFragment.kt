package id.riverflows.moviicat.ui.home.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.moviicat.data.entity.TvEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.databinding.FragmentTvBinding
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.ui.adapter.TvListAdapter
import id.riverflows.moviicat.ui.decoration.SpaceItemDecoration
import id.riverflows.moviicat.ui.detail.tv.DetailTvActivity
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilErrorMessage
import id.riverflows.moviicat.util.UtilIdlingResource
import id.riverflows.moviicat.util.UtilSnackBar

class TvFragment : Fragment(), TvListAdapter.OnItemClickCallback {
    private var _binding: FragmentTvBinding? = null
    private val binding
        get() = _binding as FragmentTvBinding
    private lateinit var viewModel: TvViewModel
    private val rvAdapter = TvListAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
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
        viewModel.getTvList()
        setLoadingState(true)
    }

    private fun bindInterface(){
        rvAdapter.setItemClickCallback(this)
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]
    }

    private fun observeViewModel(){
        viewModel.tvList.observe(viewLifecycleOwner){
            setLoadingState(false)
            when(it){
                is Resource.Success -> {
                    bindRecyclerView(it.value.data)
                }
                is Resource.Failure -> {
                    val message = UtilErrorMessage.getErrorMessage(requireContext(), it.code)
                    UtilSnackBar.showIndeterminate(binding.root, message)
                }
            }
        }
    }

    private fun setupRecyclerView(){
        with(binding.rvTvShows){
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, UtilConstants.GRID_TV_COUNT)
            addItemDecoration(SpaceItemDecoration(UtilConstants.SPACE_ITEM_DECORATION))
            adapter = rvAdapter
        }
    }

    private fun bindRecyclerView(list: List<TvEntity>){
        rvAdapter.setList(list)
    }

    override fun onItemClicked(data: TvEntity) {
        startActivity(Intent(context, DetailTvActivity::class.java).putExtra(UtilConstants.EXTRA_TV_ID, data.id))
    }

    private fun setLoadingState(isLoading: Boolean){
        with(binding.shimmerContainer){
            visibility = if(isLoading){
                UtilIdlingResource.increment()
                startShimmerAnimation()
                View.VISIBLE
            }else{
                UtilIdlingResource.decrement()
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