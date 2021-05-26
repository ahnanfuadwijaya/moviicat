package id.riverflows.moviicat.ui.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.riverflows.moviicat.R
import id.riverflows.moviicat.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding as FragmentSearchBinding
    private lateinit var pagerAdapter: SearchSectionsPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI(){
        with(binding){
            tabs.addOnTabSelectedListener(tabSelectedListener)
            pagerAdapter = SearchSectionsPagerAdapter(parentFragmentManager, lifecycle)
            binding.viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                val titles = resources.getStringArray(R.array.movie_tv_tab_titles)
                tab.text = titles[position]
            }.attach()
            searchView.setOnQueryTextListener(onQueryTextListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            searchMovieOrTv(binding.searchView.query.toString())
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                searchMovieOrTv(it)
                return true
            }
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean = false
    }

    private fun searchMovieOrTv(query: String){
        if(query.isBlank()) return

        when(binding.viewPager.currentItem){
            0 -> pagerAdapter.searchMovie(query)
            else -> pagerAdapter.searchTv(query)
        }
    }
}