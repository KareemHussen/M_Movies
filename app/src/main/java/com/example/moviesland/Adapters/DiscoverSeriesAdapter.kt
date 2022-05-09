import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesland.R
import com.example.moviesland.Models.SeriesModel.SeriesResult
import kotlinx.android.synthetic.main.movie_item.view.*


class DiscoverSeriesAdapter : ListAdapter<SeriesResult, DiscoverSeriesAdapter.viewHolder>(Diff()) {
    

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent:  ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.apply {


            if (current.backdrop_path == null){
                movieImg.setImageResource(R.drawable.notimgicon)
            } else {
                Glide.with(this).load("https://image.tmdb.org/t/p/original${current.poster_path}").into(movieImg)
            }

            movieTitle.text = current.name

            setOnClickListener {
                onItemClickListner?.let {
                    it(current)
                }
            }

        }
    }

    private var onItemClickListner: ((SeriesResult) -> Unit)? = null

    fun setOnItemClickListner(listner: (SeriesResult) -> Unit) {
        onItemClickListner = listner
    }


}


class Diff() : DiffUtil.ItemCallback<SeriesResult>() {
    override fun areItemsTheSame(oldItem: SeriesResult, newItem: SeriesResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SeriesResult, newItem: SeriesResult): Boolean {
        return oldItem == newItem
    }

}