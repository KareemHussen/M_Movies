import android.provider.ContactsContract.Directory.PACKAGE_NAME
import com.example.moviesland.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.episode_item.view.*

class EpisodeAdapter() : ListAdapter<Int, EpisodeAdapter.viewHolder>(Difff()) {

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.episode_item, parent, false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var current = getItem(position)

        holder.itemView.apply {

            EpisodeNum.text = current.toString()

            setOnClickListener {
                onItemClickListner?.let {
                    it(current)
                }
            }

        }
    }

    private var onItemClickListner: ((Int) -> Unit)? = null

    fun setOnItemClickListner(listner: (Int) -> Unit) {
        onItemClickListner = listner
    }


}


class Difff() : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

}