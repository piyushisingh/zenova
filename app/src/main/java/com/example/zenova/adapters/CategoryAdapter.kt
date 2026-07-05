package com.example.zenova.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.zenova.R
import com.example.zenova.activites.ItemListAcitivty
import com.example.zenova.databinding.ViewholderCategoryBinding
import com.example.zenova.domain.CategoryModel

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
                // Look up the position at click time instead of using the stale `position`
                val clickedPosition = holder.bindingAdapterPosition
                if (clickedPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                val clickedItem = items[clickedPosition]

                if (selectedPosition != clickedPosition) {
                    lastSelectedPosition = selectedPosition
                    selectedPosition = clickedPosition
                    if (lastSelectedPosition != -1) notifyItemChanged(lastSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(context, ItemListAcitivty::class.java).apply {
                        putExtra("id", clickedItem.id.toString())
                        putExtra("title", clickedItem.title)
                    }
                    context.startActivity(intent)
                }, 500)
            }
            val isSelected = selectedPosition == position
            root.setBackgroundResource(
                if (isSelected) R.drawable.black_bg else R.drawable.purple_bg
            )
            titletxt.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    if (isSelected) R.color.white else R.color.black
                )
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