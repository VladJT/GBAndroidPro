package jt.projects.gbandroidpro.presentation.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.domain.toOneString

class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<DataModel>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    fun setData(newData: List<DataModel>) {
        this.data = newData
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main_recyclerview_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(itemView) {
                    findViewById<TextView>(R.id.header_textview_recycler_item).text = data.text
                    findViewById<TextView>(R.id.description_textview_recycler_item).text =
                        data.meanings?.toOneString()
                    setOnClickListener { openInNewWindow(data) }
                }
            }
        }
    }

    // Передаём событие в MainActivity
    private fun openInNewWindow(listItemData: DataModel) {
        onListItemClickListener.onItemClick(listItemData)
    }

    // Определяем интерфейс обратного вызова
    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}