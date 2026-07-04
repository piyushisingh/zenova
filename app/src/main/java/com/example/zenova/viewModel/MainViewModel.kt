package com.example.zenova.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.zenova.domain.BannerModel
import com.example.zenova.domain.CategoryModel
import com.example.zenova.domain.ProfileModel
import com.example.zenova.repository.MainRepository

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    val category: LiveData<List<CategoryModel>> = repository.category
    val banner: LiveData<List<BannerModel>> = repository.banner
    val profile: LiveData<ProfileModel> = repository.profile

    fun loadCategory() = repository.loadCategories()
    fun loadBanner() = repository.loadBanner()
    fun loadProfile() = repository.loadProfile()
    fun loadItems(categoryId: String) = repository.loadItemsByCategory(categoryId)
}