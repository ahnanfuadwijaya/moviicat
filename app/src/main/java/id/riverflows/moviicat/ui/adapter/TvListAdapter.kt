package id.riverflows.moviicat.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.riverflows.moviicat.R
import id.riverflows.moviicat.data.entity.TvDetailEntity
import id.riverflows.moviicat.data.entity.TvEntity
import id.riverflows.moviicat.databinding.ItemTvGridBinding
import id.riverflows.moviicat.di.Injection
import id.riverflows.moviicat.util.UtilConstants

class TvListAdapter: RecyclerView.Adapter<TvListAdapter.TvHolder>() {
    private val list = mutableListOf<TvEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setList(listParam: List<TvEntity>){
        if(list.size > 0) list.clear()
        list.addAll(listParam)
        notifyDataSetChanged()
    }

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        return TvHolder(
            ItemTvGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TvHolder, position: Int) {
        holder.bindView(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class TvHolder(private val binding: ItemTvGridBinding): RecyclerView.ViewHolder(binding.root){
        fun bindView(data: TvEntity){
            val context = itemView.context
            val posterPath = "${Injection.providePosterPath()}${data.posterPath}"
            with(binding){
                Glide.with(context)
                    .load(posterPath)
                    .apply(RequestOptions().placeholder(R.drawable.ic_loading))
                    .error(R.drawable.ic_broken_image)
                    .into(ivPoster)
                tvName.text = data.name
                tvFirstAirDate.text = data.firstAirDate
                tvScore.text = data.voteAverage.toString()
                if(data.voteAverage < 7.5){
                    binding.ivScore.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_score_red))
                }
            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: TvEntity)
    }
}