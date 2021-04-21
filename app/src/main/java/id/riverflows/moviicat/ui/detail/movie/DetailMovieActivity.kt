package id.riverflows.moviicat.ui.detail.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import id.riverflows.moviicat.R
import id.riverflows.moviicat.data.entity.GenreEntity
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.databinding.ActivityDetailMovieBinding
import id.riverflows.moviicat.util.UtilConstants

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var movie: MovieDetailEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_detail_movie)
        val movieId = intent.getIntExtra(UtilConstants.EXTRA_MOVIE_ID, 0)
        obtainViewModel()
        observeViewModel()
        viewModel.getMovie(movieId)
    }

    private fun obtainViewModel(){
        val factory = ViewModelProvider.NewInstanceFactory()
        viewModel = ViewModelProvider(viewModelStore, factory)[DetailMovieViewModel::class.java]
    }

    private fun observeViewModel(){
        viewModel.movie.observe(this){
            movie = it
            bindData()
        }
    }

    private fun bindData(){
        val posterResource = resources.getIdentifier(movie.posterPath, UtilConstants.DEF_TYPE_RAW, packageName)
        with(binding){
            Glide.with(this@DetailMovieActivity)
                .load(posterResource)
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
        if(item.itemId == R.id.menu_share){
            shareMovie()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareMovie(){
        if(!this::movie.isInitialized) return
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, movie.title)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.title_share))
        startActivity(shareIntent)
    }
}