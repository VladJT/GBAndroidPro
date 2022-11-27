package jt.projects.gbandroidpro.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.model.domain.DataModel

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

    fun openInNewWindow(data: DataModel) {
        onListItemClickListener.onItemClick(data)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(itemView) {
                    findViewById<TextView>(R.id.header_textview_recycler_item).text = data.text
                    findViewById<TextView>(R.id.description_textview_recycler_item).text =
                        data.meanings?.get(0)?.translation?.translation
                    setOnClickListener { openInNewWindow(data) }
                }
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}