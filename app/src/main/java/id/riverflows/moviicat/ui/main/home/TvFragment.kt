package id.riverflows.moviicat.ui.main.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.databinding.FragmentTvBinding
import id.riverflows.moviicat.ui.decoration.SpaceItemDecoration
import id.riverflows.moviicat.ui.detail.tv.DetailTvActivity
import id.riverflows.moviicat.ui.main.TvViewModel
import id.riverflows.moviicat.util.UtilConstants

class TvFragment : Fragment(), TvListAdapter.OnItemClickCallback {
    private var _binding: FragmentTvBinding? = null
    private val binding
        get() = _binding as FragmentTvBinding
    private lateinit var viewModel: TvViewModel
    private val rvAdapter = TvListAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAdapter.setItemClickCallback(this)
        obtainViewModel()
        observeViewModel()
        initiateRecyclerView()
        viewModel.getTvList()
    }

    private fun obtainViewModel(){
        val factory = ViewModelProvider.NewInstanceFactory()
        viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]
    }

    private fun observeViewModel(){
        viewModel.tvList.observe(viewLifecycleOwner){
            bindRecyclerView(it)
        }
    }

    private fun initiateRecyclerView(){
        with(binding.rvTvShows){
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, UtilConstants.GRID_TV_COUNT)
            addItemDecoration(SpaceItemDecoration(UtilConstants.SPACE_ITEM_DECORATION))
            adapter = rvAdapter
        }
    }

    private fun bindRecyclerView(list: List<TvDetailEntity>){
        rvAdapter.setList(list)
    }

    override fun onItemClicked(data: TvDetailEntity) {
        startActivity(Intent(context, DetailTvActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}