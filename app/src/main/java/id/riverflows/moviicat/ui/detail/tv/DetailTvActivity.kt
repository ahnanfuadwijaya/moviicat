package id.riverflows.moviicat.ui.detail.tv

import android.os.Bundle
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
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.data.source.remote.response.TvDetailResponse
import id.riverflows.moviicat.databinding.ActivityDetailBinding
import id.riverflows.moviicat.di.Injection
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.util.*
import timber.log.Timber
import java.util.*

class DetailTvActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailTvViewModel
    private var tvName = ""
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
        supportActionBar?.title = getString(R.string.title_detail_tv_show)
        binding.fabFavorite.setOnClickListener {
            favoriteData?.let {
                if(isFavorite) viewModel.removeFavorite(it) else viewModel.insertFavorite(it)}
        }
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(viewModelStore, factory)[DetailTvViewModel::class.java]
    }

    private fun requestData(){
        val tvId = intent.getLongExtra(UtilConstants.EXTRA_TV_ID, 0)
        viewModel.getTv(tvId)
        setLoadingState(true)
        getFavoriteState(tvId)
        UtilIdlingResource.increment()
    }

    private fun getFavoriteState(id: Long){
        viewModel.findFavoriteByIdAndType(id, UtilConstants.TYPE_TV)
    }

    private fun observeViewModel(){
        viewModel.tv.observe(this){
            setLoadingState(false)
            UtilIdlingResource.decrement()
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

    private fun bindData(data: TvDetailResponse){
        tvName = data.name
        val date = Date().time
        favoriteData = FavoriteEntity(data.id, data.name, data.voteAverage, data.firstAirDate, data.posterPath?:"", date, UtilConstants.TYPE_TV)
        with(binding){
            val posterPath = "${Injection.provideOriginalPosterPath()}${data.posterPath}"
            Glide.with(this@DetailTvActivity)
                    .load(posterPath)
                    .apply(RequestOptions().placeholder(R.drawable.ic_loading))
                    .error(R.drawable.ic_broken_image)
                    .override(UtilConstants.DETAIL_POSTER_WIDTH, UtilConstants.DETAIL_POSTER_HEIGHT)
                    .into(ivPoster)
            tvTitle.text = data.name
            tvPopularity.text = getString(R.string.field_popularity, data.popularity)
            tvRate.text = data.voteAverage.toString()
            tvDate.text = getString(R.string.field_airing_date, data.firstAirDate, data.lastAirDate)
            tvStatus.text = data.status
            inflateChips(data.genres)
            tvValueOverview.text = data.overview
        }
        Timber.d(favoriteData.toString())
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
        if(item.itemId == R.id.menu_share) shareTv()
        return super.onOptionsItemSelected(item)
    }

    private fun shareTv(){
        UtilShare.share(this, tvName)
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