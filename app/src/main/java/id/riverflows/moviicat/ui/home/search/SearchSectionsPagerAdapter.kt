package id.riverflows.moviicat.ui.home.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchSectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    private val movieFragment = SearchMovieFragment()
    private val tvFragment = SearchTvFragment()
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment = if(position==0) movieFragment  else tvFragment
    fun searchMovie(query: String){
        movieFragment.searchMovie(query)
    }
    fun searchTv(query: String){
        tvFragment.searchTv(query)
    }
}