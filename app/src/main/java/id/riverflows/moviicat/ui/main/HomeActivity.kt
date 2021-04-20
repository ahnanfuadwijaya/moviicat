package id.riverflows.moviicat.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import id.riverflows.moviicat.databinding.ActivityHomeBinding
import id.riverflows.moviicat.ui.main.home.HomeSectionsPagerAdapter

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var pagerAdapter: HomeSectionsPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pagerAdapter = HomeSectionsPagerAdapter(this)
        bindViewPager()
        obtainViewModel()
    }
    private fun obtainViewModel(){
        val factory = ViewModelProvider.NewInstanceFactory()
        viewModel = ViewModelProvider(viewModelStore, factory)[MovieViewModel::class.java]
    }

    private fun bindViewPager(){
        with(binding){
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                val titles = resources.getStringArray(id.riverflows.moviicat.R.array.home_tab_titles)
                tab.text = titles[position]
            }.attach()
        }
    }
}