package com.example.zenova.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.zenova.domain.CategoryModel
import com.example.zenova.domain.ProfileModel
import com.example.zenova.repository.MainRepository

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    val category: LiveData<List<CategoryModel>> = repository.category
    val profile: LiveData<ProfileModel> = repository.profile

    fun loadCategory() = repository.loadCategories()
    fun loadProfile() = repository.loadProfile()
}