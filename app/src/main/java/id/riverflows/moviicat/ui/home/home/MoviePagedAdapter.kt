package id.riverflows.moviicat.ui.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.riverflows.moviicat.R
import id.riverflows.moviicat.data.entity.MovieEntity
import id.riverflows.moviicat.databinding.ItemHomeGridBinding
import id.riverflows.moviicat.di.Injection

class MoviePagedAdapter: PagingDataAdapter<MovieEntity, MoviePagedAdapter.MovieViewHolder>(
    MovieComparator
) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }



    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindView(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
                ItemHomeGridBinding.bind(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.item_home_grid,
                                parent,
                                false
                        )
                )
        )
    }
    inner class MovieViewHolder(private val binding: ItemHomeGridBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(data: MovieEntity){
            val context = itemView.context
            val posterPath = "${Injection.providePosterPath()}${data.posterPath}"
            with(binding){
                Glide.with(context)
                        .asBitmap()
                        .load(posterPath)
                        .apply(RequestOptions().override(100,150).placeholder(R.drawable.ic_loading))
                        .error(R.drawable.ic_broken_image)
                        .into(ivPoster)
                tvTitle.text = data.title
                tvDate.text = data.releaseDate
                tvScore.text = data.voteAverage.toString()
                if(data.voteAverage < 7.5){
                    binding.ivScore.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_score_red))
                }else{
                    binding.ivScore.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_score))
                }
            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: MovieEntity)
    }

    companion object MovieComparator : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }
}