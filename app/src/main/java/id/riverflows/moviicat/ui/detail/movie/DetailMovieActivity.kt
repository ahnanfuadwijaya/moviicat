package id.riverflows.moviicat.ui.detail.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import id.riverflows.moviicat.R
import id.riverflows.moviicat.data.entity.GenreEntity
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.MovieDetailResponse
import id.riverflows.moviicat.databinding.ActivityDetailBinding
import id.riverflows.moviicat.di.Injection
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.util.*
import timber.log.Timber
import java.util.*

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailMovieViewModel
    private var movieTitle = ""
    private var favoriteData: FavoriteEntity? = null
    private var isFavorite = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        obtainViewModel()
        observeViewModel()
        requestData()
    }
    
    private fun setupView(){
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_detail_movie)
        binding.fabFavorite.setOnClickListener {
            favoriteData?.let {
                if(isFavorite) viewModel.removeFavorite(it) else viewModel.insertFavorite(it)}
        }
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(viewModelStore, factory)[DetailMovieViewModel::class.java]
    }

    private fun requestData(){
        val movieId = intent.getLongExtra(UtilConstants.EXTRA_MOVIE_ID, 0)
        viewModel.getMovie(movieId)
        setLoadingState(true)
        getFavoriteState(movieId)
    }

    private fun getFavoriteState(id: Long){
        viewModel.findFavoriteByIdAndType(id, UtilConstants.TYPE_MOVIE)
    }

    private fun observeViewModel(){
        viewModel.movie.observe(this){
            setLoadingState(false)
            when(it){
                is Resource.Success -> {
                    bindData(it.value)
                }
                is Resource.Failure -> {
                    val message = UtilErrorMessage.getErrorMessage(this, it.code)
                    UtilSnackBar.showIndeterminate(binding.root, message)
                }
            }
        }
        viewModel.insertResult.observe(this){
            isFavorite = if(it>0){
                Timber.d("Insert Success")
                true
            }else{
                Timber.d("Insert Failed")
                false
            }
            setFabState(isFavorite)
        }
        viewModel.removeResult.observe(this){
            if(it>0){
                isFavorite = false
                setFabState(isFavorite)
                Timber.d("Remove Success")
            }else{
                Timber.d("Remove Failed")
            }
        }
        viewModel.isFavorite.observe(this){
            isFavorite = it != null
            setFabState(isFavorite)
        }
    }

    private fun bindData(data: MovieDetailResponse){
        movieTitle = data.title
        val date = Date().time
        favoriteData = FavoriteEntity(data.id, data.title, data.voteAverage, data.releaseDate, data.posterPath?:"", date, UtilConstants.TYPE_MOVIE)
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
            tvDate.text = data.releaseDate
            tvStatus.text = data.status
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
                UtilIdlingResource.increment()
                viewContainer.visibility = View.INVISIBLE
                shimmerContainer.visibility = View.VISIBLE
                shimmerContainer.startShimmerAnimation()
            }else{
                UtilIdlingResource.decrement()
                viewContainer.visibility = View.VISIBLE
                shimmerContainer.visibility = View.INVISIBLE
                shimmerContainer.stopShimmerAnimation()
            }
        }
    }
    private fun setFabState(isFavorite: Boolean){
        with(binding.fabFavorite){
            if(isFavorite){
                setImageResource(R.drawable.ic_favorite)
            }else{
                setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }
}