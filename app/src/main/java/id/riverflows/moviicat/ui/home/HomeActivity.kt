package id.riverflows.moviicat.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.riverflows.moviicat.R
import id.riverflows.moviicat.databinding.ActivityHomeBinding
import id.riverflows.moviicat.ui.home.home.HomeFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, HomeFragment())
            .commit()
    }


}