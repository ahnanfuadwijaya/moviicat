package id.riverflows.moviicat.ui.home.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeSectionsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    private val movieFragment = GridFragment.newInstance(GridFragment.TYPE_MOVIE)
    private val tvFragment = GridFragment.newInstance(GridFragment.TYPE_TV)
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment = if(position==0) movieFragment  else tvFragment
}