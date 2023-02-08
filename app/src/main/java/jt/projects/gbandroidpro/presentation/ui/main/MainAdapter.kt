package jt.projects.gbandroidpro.presentation.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jt.projects.gbandroidpro.databinding.ActivityMainRecyclerviewItemBinding
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
        val binding = ActivityMainRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ActivityMainRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(binding) {
                    headerTextviewRecyclerItem.text = data.text
                    descriptionTextviewRecyclerItem.text =
                        data.meanings?.toOneString()
                    root.setOnClickListener { openInNewWindow(data) }
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