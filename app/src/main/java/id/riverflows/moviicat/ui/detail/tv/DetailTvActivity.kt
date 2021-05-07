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
import id.riverflows.moviicat.data.source.remote.response.TvDetailResponse
import id.riverflows.moviicat.data.source.remote.Resource
import id.riverflows.moviicat.databinding.ActivityDetailTvBinding
import id.riverflows.moviicat.di.Injection
import id.riverflows.moviicat.factory.ViewModelFactory
import id.riverflows.moviicat.util.*
import timber.log.Timber

class DetailTvActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTvBinding
    private lateinit var viewModel: DetailTvViewModel
    private var tvName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        obtainViewModel()
        observeViewModel()
        requestData()
    }

    private fun setupView(){
        binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_detail_tv_show)
    }

    private fun obtainViewModel(){
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(viewModelStore, factory)[DetailTvViewModel::class.java]
    }

    private fun requestData(){
        val tvId = intent.getLongExtra(UtilConstants.EXTRA_TV_ID, 0)
        viewModel.getTv(tvId)
        setLoadingState(true)
        EspressoIdlingResource.increment()
    }

    private fun observeViewModel(){
        viewModel.tv.observe(this){
            setLoadingState(false)
            EspressoIdlingResource.decrement()
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
    }

    private fun bindData(data: TvDetailResponse){
        tvName = data.name
        with(binding){
            val posterPath = "${Injection.provideOriginalPosterPath()}${data.posterPath}"
            Glide.with(this@DetailTvActivity)
                    .load(posterPath)
                    .apply(RequestOptions().placeholder(R.drawable.ic_loading))
                    .error(R.drawable.ic_broken_image)
                    .override(UtilConstants.DETAIL_POSTER_WIDTH, UtilConstants.DETAIL_POSTER_HEIGHT)
                    .into(ivPoster)
            tvName.text = data.name
            tvPopularity.text = getString(R.string.field_popularity, data.popularity)
            tvRate.text = data.voteAverage.toString()
            tvAiringDate.text = getString(R.string.field_airing_date, data.firstAirDate, data.lastAirDate)
            tvAiringStatus.text = data.status
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
        if(item.itemId == R.id.menu_share) shareTv()
        return super.onOptionsItemSelected(item)
    }

    private fun shareTv(){
        UtilShare.share(this, tvName)
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