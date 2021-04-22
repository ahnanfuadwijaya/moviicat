package id.riverflows.moviicat.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.riverflows.moviicat.ui.home.movie.MovieFragment
import id.riverflows.moviicat.ui.home.tv.TvFragment

class HomeSectionsPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment = if(position==0) MovieFragment()  else TvFragment()
}