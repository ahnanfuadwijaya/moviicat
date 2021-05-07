package id.riverflows.moviicat.ui.detail.movie

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import id.riverflows.moviicat.R
import id.riverflows.moviicat.data.entity.GenreEntity
import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.databinding.ActivityDetailMovieBinding
import id.riverflows.moviicat.di.Injection
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.util.UtilConstants
import id.riverflows.moviicat.util.UtilErrorMessage
import id.riverflows.moviicat.util.UtilShare
import id.riverflows.moviicat.util.UtilSnackBar
import timber.log.Timber

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel
    private var movieTitle = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        obtainViewModel()
        observeViewModel()
        requestData()
    }
    
    private fun setupView(){
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_detail_movie)
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(viewModelStore, factory)[DetailMovieViewModel::class.java]
    }

    private fun requestData(){
        val movieId = intent.getLongExtra(UtilConstants.EXTRA_MOVIE_ID, 0)
        viewModel.getMovie(movieId)
        setLoadingState(true)
    }

    private fun observeViewModel(){
        viewModel.movie.observe(this){
            setLoadingState(false)
            when(it){
                is Resource.Success -> {
                    bindData(it.value)
                    Timber.d("BindData")
                }
                is Resource.Failure -> {
                    val message = UtilErrorMessage.getErrorMessage(this, it.code)
                    UtilSnackBar.showIndeterminate(binding.root, message)
                }
            }
        }
    }

    private fun bindData(data: MovieDetailResponse){
        movieTitle = data.title
        with(binding){
            val posterPath = "${Injection.provideOriginalPosterPath()}${data.posterPath}"
            Glide.with(this@DetailMovieActivity)
                .load(posterPath)
                .apply(RequestOptions().placeholder(R.drawable.ic_loading))
                .error(R.drawable.ic_broken_image)
                .override(UtilConstants.DETAIL_POSTER_WIDTH,UtilConstants.DETAIL_POSTER_HEIGHT)
                .into(ivPoster)
            tvTitle.text = data.title
            tvPopularity.text = getString(R.string.field_popularity, data.popularity)
            tvRate.text = data.voteAverage.toString()
            tvReleaseDate.text = data.releaseDate
            tvReleaseStatus.text = data.status
            inflateChips(data.genres)
            tvValueOverview.text = data.overview
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
        UtilShare.share(this, movieTitle)
    }

    private fun setLoadingState(isLoading: Boolean){
        with(binding){
            if(isLoading){
                viewContainer.visibility = View.INVISIBLE
                shimmerContainer.visibility = View.VISIBLE
                shimmerContainer.startShimmerAnimation()
            }else{
                viewContainer.visibility = View.VISIBLE
                shimmerContainer.visibility = View.INVISIBLE
                shimmerContainer.stopShimmerAnimation()
            }
        }
    }
}