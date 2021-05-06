package id.riverflows.moviicat.ui.detail.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import id.riverflows.moviicat.R
import id.riverflows.moviicat.data.entity.GenreEntity
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.databinding.ActivityDetailMovieBinding
import id.riverflows.moviicat.di.Injection
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilErrorMessage
import id.riverflows.moviicat.util.UtilShare
import id.riverflows.moviicat.util.UtilSnackBar

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var movie: MovieDetailEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_detail_movie)
        val movieId = intent.getLongExtra(UtilConstants.EXTRA_MOVIE_ID, 0)
        obtainViewModel()
        observeViewModel()
        viewModel.getMovie(movieId)
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(viewModelStore, factory)[DetailMovieViewModel::class.java]
    }

    private fun observeViewModel(){
        viewModel.movie.observe(this){
            when(it){
                is Resource.Success -> {
                    movie = it.value
                    bindData()
                }
                is Resource.Failure -> {
                    val message = UtilErrorMessage.getErrorMessage(this, it.code)
                    UtilSnackBar.showIndeterminate(binding.root, message)
                }
            }
        }
    }

    private fun bindData(){
        with(binding){
            val posterPath = "${Injection.provideOriginalPosterPath()}${movie.posterPath}"
            Glide.with(this@DetailMovieActivity)
                .load(posterPath)
                .apply(RequestOptions().placeholder(R.drawable.ic_loading))
                .error(R.drawable.ic_broken_image)
                .override(UtilConstants.DETAIL_POSTER_WIDTH,UtilConstants.DETAIL_POSTER_HEIGHT)
                .into(ivPoster)
            tvTitle.text = movie.title
            tvPopularity.text = getString(R.string.field_popularity, movie.popularity)
            tvRate.text = movie.voteAverage.toString()
            tvReleaseDate.text = movie.releaseDate
            tvReleaseStatus.text = movie.status
            inflateChips(movie.genres)
            tvValueOverview.text = movie.overview
        }
    }

    private fun inflateChips(list: List<GenreEntity>){
        for(item in list){
            val chip = layoutInflater.inflate(R.layout.item_chip_genre, null, false) as Chip
            chip.text = item.name
            binding.chipGenres.addView(chip)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_share) shareMovie()
        return super.onOptionsItemSelected(item)
    }

    private fun shareMovie(){
        if(!this::movie.isInitialized) return
        UtilShare.share(this, movie.title)
    }
}