package id.riverflows.moviicat.ui.home.favorite

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
import id.riverflows.moviicat.data.source.local.room.FavoriteEntity
import id.riverflows.moviicat.databinding.ItemFavoriteListBinding
import id.riverflows.moviicat.databinding.ItemHomeGridBinding
import id.riverflows.moviicat.di.Injection

class FavoritePagedListAdapter: PagingDataAdapter<FavoriteEntity, FavoritePagedListAdapter.FavoriteViewHolder>(
    FavoriteComparator
) {

    companion object FavoriteComparator : DiffUtil.ItemCallback<FavoriteEntity>() {
        override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem == newItem
        }
    }
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindView(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
                ItemFavoriteListBinding.bind(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.item_favorite_list,
                                parent,
                                false
                        )
                )
        )
    }
    inner class FavoriteViewHolder(private val binding: ItemFavoriteListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(data: FavoriteEntity){
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
                tvDate.text = data.date
                tvType.text = data.type
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
        fun onItemClicked(data: FavoriteEntity)
    }
}