package id.riverflows.moviicat.ui.main.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.riverflows.moviicat.R
import id.riverflows.moviicat.app.App
import id.riverflows.moviicat.data.entity.MovieDetailEntity
import id.riverflows.moviicat.databinding.ItemMovieGridBinding
import timber.log.Timber


class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {
    private val list = mutableListOf<MovieDetailEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setList(listParam: List<MovieDetailEntity>){
        if(list.isNotEmpty()) list.clear()
        list.addAll(listParam)
        notifyDataSetChanged()
    }

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
                ItemMovieGridBinding.bind(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_movie_grid,
                        parent,
                        false
                    )
                )
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindView(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MovieHolder(private val binding: ItemMovieGridBinding): RecyclerView.ViewHolder(
            binding.root
    ){
        fun bindView(data: MovieDetailEntity){
            val context = itemView.context
            val uri = Uri.withAppendedPath(App.DRAWABLE_URI, data.posterPath)
            Timber.d(uri.toString())
            with(binding){
                Glide.with(context)
                        .load(uri)
                        .apply(RequestOptions().placeholder(R.drawable.ic_loading))
                        .error(R.drawable.ic_broken_image)
                        .into(ivPoster)
                tvTitle.text = data.title
                tvReleaseDate.text = data.releaseDate
                tvScore.text = data.voteAverage.toString()
                if(data.voteAverage < 7.5){
                    binding.ivScore.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_score_red))
                }
            }
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: MovieDetailEntity)
    }
}