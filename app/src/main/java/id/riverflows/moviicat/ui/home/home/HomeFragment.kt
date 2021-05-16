package id.riverflows.moviicat.ui.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.riverflows.moviicat.R
import id.riverflows.moviicat.databinding.FragmentMovieTvRootBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentMovieTvRootBinding? = null
    private val binding
        get() = _binding as FragmentMovieTvRootBinding
    private lateinit var pagerAdapter: HomeSectionsPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieTvRootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI(){
        with(binding){
            pagerAdapter = HomeSectionsPagerAdapter(parentFragmentManager, lifecycle)
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
}