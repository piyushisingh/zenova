package com.example.zenova.activites

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zenova.adapters.ItemListCategoryAdapter
import com.example.zenova.databinding.ActivityItemListAcitivtyBinding
import com.example.zenova.viewModel.MainViewModel

class ItemListAcitivty : AppCompatActivity() {

    private lateinit var binding: ActivityItemListAcitivtyBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private var categoryId: String = ""
    private var categoryTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItemListAcitivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getBundle()
        initList()
    }

    private fun getBundle() {
        categoryId = intent.getStringExtra("id") ?: ""
        categoryTitle = intent.getStringExtra("title") ?: ""

        binding.categoryTitleTxt.text = categoryTitle
    }

    private fun initList() {
        binding.apply {
            progressBar.visibility = View.VISIBLE

            viewModel.loadItems(categoryId).observe(this@ItemListAcitivty) { list ->
                itemsView.layoutManager = LinearLayoutManager(
                    this@ItemListAcitivty,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                itemsView.adapter = ItemListCategoryAdapter(list)
                progressBar.visibility = View.GONE
            }

            backBtn.setOnClickListener { finish() }
        }
    }
}