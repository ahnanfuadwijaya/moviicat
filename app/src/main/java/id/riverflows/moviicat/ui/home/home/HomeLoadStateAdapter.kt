package id.riverflows.moviicat.ui.home.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.riverflows.moviicat.R
import id.riverflows.moviicat.databinding.HeaderFooterLoadingStateBinding
import id.riverflows.moviicat.databinding.ShimmerItemGridContainerBinding

class HomeLoadStateAdapter: LoadStateAdapter<HomeLoadStateAdapter.LoadStateHolder>() {
    inner class LoadStateHolder(private val binding: HeaderFooterLoadingStateBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState) {
            if(loadState is LoadState.Loading){
                binding.btnRetry.visibility = View.GONE
                binding.progressbar.visibility = View.VISIBLE
            }
            if(loadState is LoadState.Error){
                binding.btnRetry.visibility = View.VISIBLE
                binding.progressbar.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(holder: LoadStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error || loadState is LoadState.NotLoading
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateHolder {
        return LoadStateHolder(
            HeaderFooterLoadingStateBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.header_footer_loading_state,
                parent,
                false
            )
        ))
    }
}