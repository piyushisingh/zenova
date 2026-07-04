package com.example.zenova.activites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.example.zenova.adapters.CategoryAdapter
import com.example.zenova.databinding.ActivityMainBinding
import com.example.zenova.viewModel.MainViewModel
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(owner = this)[MainViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding
    private val categoryAdapter = CategoryAdapter(items = mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProfile()
        initCategory()
        initBanner()
    }

    private fun initCategory() {
        binding.apply {
            catView.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            catView.adapter = categoryAdapter
            viewModel.loadCategory()
            viewModel.category.observe(this@MainActivity) {
                progressBar.visibility = View.GONE
                categoryAdapter.updateData(newData = it)
            }
        }
    }

    private fun initProfile() {
        viewModel.loadProfile()
        viewModel.profile.observe(this) {
            binding.nameTxt.text = it.name
            Glide.with(this@MainActivity).load(it.profilePic).into(binding.profilePic)
        }
    }

    private fun initBanner() {
        viewModel.loadBanner()
        viewModel.banner.observe(this) {
            val bannerUrl = it.firstOrNull()?.url
            Glide.with(this@MainActivity).load(bannerUrl).into(binding.bannerImg)
        }
    }
}