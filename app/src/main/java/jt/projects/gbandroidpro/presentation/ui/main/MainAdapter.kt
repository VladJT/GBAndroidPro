package jt.projects.gbandroidpro.presentation.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jt.projects.gbandroidpro.databinding.ActivityMainRecyclerviewItemBinding
import jt.projects.model.data.DataModel

class MainAdapter(
    private var onListItemClick: (DataModel) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var data: List<DataModel> = arrayListOf()

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
                        data.meanings
                    root.setOnClickListener { openInNewWindow(data) }
                }

            }
        }
    }

    // Передаём событие в MainActivity
    private fun openInNewWindow(listItemData: DataModel) {
        onListItemClick(listItemData)
    }
}