package id.riverflows.moviicat.ui.detail.tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.riverflows.moviicat.R
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.databinding.ActivityDetailTvBinding
import id.riverflows.moviicat.util.UtilConstants

class DetailTvActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTvBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<TvDetailEntity>(UtilConstants.EXTRA_TV)
        data?.let {
            supportActionBar?.title = it.name
            bindData(it)
        }
    }
    private fun bindData(data: TvDetailEntity){}
}