package id.riverflows.moviicat.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import id.riverflows.moviicat.R
import id.riverflows.moviicat.databinding.ActivityHomeBinding
import id.riverflows.moviicat.ui.adapter.HomeSectionsPagerAdapter
import id.riverflows.moviicat.ui.home.movie.MovieViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var pagerAdapter: HomeSectionsPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindInterface()
        bindViewPager()
    }

    private fun bindInterface(){
        pagerAdapter = HomeSectionsPagerAdapter(this)
    }

    private fun bindViewPager(){
        with(binding){
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                val titles = resources.getStringArray(R.array.home_tab_titles)
                tab.text = titles[position]
            }.attach()
        }
    }
}