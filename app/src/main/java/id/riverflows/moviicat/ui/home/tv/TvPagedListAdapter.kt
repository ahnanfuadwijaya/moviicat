package id.riverflows.moviicat.ui.home.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.riverflows.moviicat.R
import id.riverflows.moviicat.data.entity.TvEntity
import id.riverflows.moviicat.databinding.ItemHomeGridBinding
import id.riverflows.moviicat.di.Injection

class TvPagedListAdapter: PagingDataAdapter<TvEntity, TvPagedListAdapter.TvViewHolder>(
    TvComparator
) {

    companion object TvComparator : DiffUtil.ItemCallback<TvEntity>() {
        override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
            return oldItem == newItem
        }
    }
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindView(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        return TvViewHolder(
            ItemHomeGridBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_home_grid,
                    parent,
                    false
                )
            )
        )
    }
    inner class TvViewHolder(private val binding: ItemHomeGridBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(data: TvEntity){
            val context = itemView.context
            val posterPath = "${Injection.providePosterPath()}${data.posterPath}"
            with(binding){
                Glide.with(context)
                    .asBitmap()
                    .load(posterPath)
                    .apply(RequestOptions().override(100,150).placeholder(R.drawable.ic_loading))
                    .error(R.drawable.ic_broken_image)
                    .into(ivPoster)
                tvTitle.text = data.name
                tvDate.text = data.firstAirDate
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
        fun onItemClicked(data: TvEntity)
    }
}