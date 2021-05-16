package id.riverflows.moviicat.ui.home.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.riverflows.moviicat.R
import id.riverflows.moviicat.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding as FragmentHomeBinding
    private lateinit var pagerAdapter: HomeSectionsPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI(){
        binding.tabs.addOnTabSelectedListener(tabSelectedListener)
        pagerAdapter = HomeSectionsPagerAdapter(parentFragmentManager, lifecycle)
        with(binding){
            binding.viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                val titles = resources.getStringArray(R.array.movie_tv_tab_titles)
                tab.text = titles[position]
            }.attach()
        }
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}