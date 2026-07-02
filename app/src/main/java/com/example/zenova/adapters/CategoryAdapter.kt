package com.example.zenova.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zenova.databinding.ViewholderCategoryBinding
import com.example.zenova.domain.CategoryModel
import android.content.Context
import com.example.zenova.R

class CategoryAdapter(private val items: MutableList<CategoryModel>)
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    inner class ViewHolder(val binding: ViewholderCategoryBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            titletxt.text = item.title
            root.setOnClickListener {
                if (selectedPosition != position) {
                    lastSelectedPosition = selectedPosition
                    selectedPosition = position
                    if (lastSelectedPosition != -1) notifyItemChanged(lastSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
            val isSelected = selectedPosition == position
            root.setBackgroundResource(
                if (isSelected) R.drawable.black_bg else R.drawable.purple_bg
            )
            titletxt.setTextColor(
                if (isSelected) holder.itemView.context.resources.getColor(R.color.white)
                else holder.itemView.context.resources.getColor(R.color.black)
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
        fun updateData(newData: List<CategoryModel>) {
            items.clear()
            items.addAll(newData)
            notifyDataSetChanged()

    }
}