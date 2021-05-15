package id.riverflows.moviicat.ui.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var query = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        query = "test"
        searchMovieOrTv()
    }

    private fun setupUI(){
        binding.tabs.addOnTabSelectedListener(tabSelectedListener)
        pagerAdapter = SearchSectionsPagerAdapter(parentFragmentManager, lifecycle)
        with(binding){
            binding.viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                val titles = resources.getStringArray(R.array.movie_tv_tab_titles)
                tab.text = titles[position]
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            searchMovieOrTv()
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }

    private fun searchMovieOrTv(){
        if(query.isBlank()) return
        when(binding.viewPager.currentItem){
            0 -> pagerAdapter.searchMovie(query)
            else -> pagerAdapter.searchTv(query)
        }
    }
}