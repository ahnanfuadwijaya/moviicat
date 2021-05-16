package id.riverflows.moviicat.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import id.riverflows.moviicat.R
import id.riverflows.moviicat.databinding.ActivityHomeBinding
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.ui.home.favorite.FavoriteFragment
import id.riverflows.moviicat.ui.home.home.HomeFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeSharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInitialView()
        setupNavController()
        obtainViewModel()
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(viewModelStore, factory)[HomeSharedViewModel::class.java]
    }

    private fun setupInitialView(){
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupNavController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        with(binding.bottomNavigationView){
            setupWithNavController(navController)
            setOnNavigationItemSelectedListener { item ->
                when(item.itemId){
                    R.id.menu_home -> {
                        item.isChecked = true
                        navController.navigate(R.id.nav_home)
                        true
                    }
                    R.id.menu_search -> {
                        item.isChecked = true
                        navController.navigate(R.id.nav_search)
                        true
                    }
                    R.id.menu_favorite -> {
                        item.isChecked = true
                        navController.navigate(R.id.nav_favorite)
                        true
                    }
                    else -> false
                }
            }
        }
    }

}