package id.riverflows.moviicat.ui.detail.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.riverflows.moviicat.R
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.databinding.ActivityDetailMovieBinding
import id.riverflows.moviicat.util.UtilConstants

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<MovieDetailEntity>(UtilConstants.EXTRA_MOVIE)
        data?.let {
            supportActionBar?.title = it.title
            bindData(it)
        }
    }
    private fun bindData(data: MovieDetailEntity){}
}