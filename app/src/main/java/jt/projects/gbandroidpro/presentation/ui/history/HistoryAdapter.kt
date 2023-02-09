package jt.projects.gbandroidpro.presentation.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.domain.toOneString

class HistoryAdapter(
    private var onListItemClick: (DataModel) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataModel> = arrayListOf()

    // Метод передачи данных в адаптер
    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.activity_history_recycler_view_item, parent,
                    false
                ) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size


    inner class RecyclerItemViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(itemView) {
                    findViewById<TextView>(R.id.header_history_textview_recycler_item).text =
                        data.text
                    findViewById<TextView>(R.id.description_history_textview_recycler_item).text =
                        data.meanings.toOneString().split(",")[0]
                    setOnClickListener {
                        openInNewWindow(data)
                    }
                }

            }
        }

        // Передаём событие в MainActivity
        private fun openInNewWindow(listItemData: DataModel) {
            onListItemClick(listItemData)
        }
    }
}
