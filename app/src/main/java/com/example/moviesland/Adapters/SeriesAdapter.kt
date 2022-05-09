import android.provider.ContactsContract.Directory.PACKAGE_NAME
import com.example.moviesland.R
import kotlinx.android.synthetic.main.season_item.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesland.Models.SeriesModel.Details.Season

class SeriesAdapter() : ListAdapter<Season, SeriesAdapter.viewHolder>(DiffU()) {

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.season_item, parent, false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var current = getItem(position)

        holder.itemView.apply {


            seasonNumb.text = current.season_number.toString()
            EpisodeNumb.text = current.episode_count.toString()



            setOnClickListener {
                onItemClickListner?.let {
                    it(current)
                }
            }

        }
    }

    private var onItemClickListner: ((Season) -> Unit)? = null

    fun setOnItemClickListner(listner: (Season) -> Unit) {
        onItemClickListner = listner
    }


}


class DiffU() : DiffUtil.ItemCallback<Season>() {
    override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
        return oldItem == newItem
    }

}