package com.example.zenova.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zenova.activites.DeatilActivity
import com.example.zenova.databinding.ViewholderItemBinding
import com.example.zenova.domain.ItemModel

class ItemListCategoryAdapter(val items: MutableList<ItemModel>) :
    RecyclerView.Adapter<ItemListCategoryAdapter.ItemViewHolder>() {

    private lateinit var context: Context

    class ItemViewHolder(val binding: ViewholderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        context = parent.context
        val binding = ViewholderItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            titleTxt.text = item.title ?: ""
            subtitleTxt.text = item.subtitle ?: ""

            Glide.with(context)
                .load(item.picUrl)
                .into(pic)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DeatilActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}

