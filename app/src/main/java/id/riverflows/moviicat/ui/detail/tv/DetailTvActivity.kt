package id.riverflows.moviicat.ui.detail.tv

import android.content.Intent
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
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.databinding.ActivityDetailTvBinding
import id.riverflows.moviicat.util.UtilConstants

class DetailTvActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTvBinding
    private lateinit var viewModel: DetailTvViewModel
    private lateinit var tv: TvDetailEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title_detail_tv_show)
        val tvId = intent.getIntExtra(UtilConstants.EXTRA_TV_ID, 0)
        obtainViewModel()
        observeViewModel()
        viewModel.getTv(tvId)
    }

    private fun obtainViewModel(){
        val factory = ViewModelProvider.NewInstanceFactory()
        viewModel = ViewModelProvider(viewModelStore, factory)[DetailTvViewModel::class.java]
    }

    private fun observeViewModel(){
        viewModel.tv.observe(this){
            tv = it
            bindData()
        }
    }

    private fun bindData(){
        with(binding){
            val posterResource = resources.getIdentifier(tv.posterPath, UtilConstants.DEF_TYPE_RAW, packageName)
            Glide.with(this@DetailTvActivity)
                    .load(posterResource)
                    .apply(RequestOptions().placeholder(R.drawable.ic_loading))
                    .error(R.drawable.ic_broken_image)
                    .override(UtilConstants.DETAIL_POSTER_WIDTH, UtilConstants.DETAIL_POSTER_HEIGHT)
                    .into(ivPoster)
            tvName.text = tv.name
            tvPopularity.text = getString(R.string.field_popularity, tv.popularity)
            tvRate.text = tv.voteAverage.toString()
            tvAiringDate.text = getString(R.string.field_airing_date, tv.firstAirDate, tv.lastAirDate)
            tvAiringStatus.text = tv.status
            inflateChips(tv.genres)
            tvValueOverview.text = tv.overview
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
            shareTv()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareTv(){
        if(!this::tv.isInitialized) return
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, tv.name)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.title_share))
        startActivity(shareIntent)
    }
}