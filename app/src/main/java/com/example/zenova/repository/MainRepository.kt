package com.example.zenova.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.zenova.domain.BannerModel
import com.example.zenova.domain.CategoryModel
import com.example.zenova.domain.ItemModel
import com.example.zenova.domain.ProfileModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query

class MainRepository {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _category = MutableLiveData<List<CategoryModel>>()
    private val _banner = MutableLiveData<List<BannerModel>>()
    private val _profile = MutableLiveData<ProfileModel>()

    val category: LiveData<List<CategoryModel>> get() = _category
    val banner: LiveData<List<BannerModel>> get() = _banner
    val profile: LiveData<ProfileModel> get() = _profile

    fun loadProfile() {
        val ref = firebaseDatabase.getReference("Profile")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").getValue(String::class.java)
                val pic = snapshot.child("profilePic").getValue(String::class.java)
                _profile.value = ProfileModel(name = name ?: "", profilePic = pic ?: "")
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun loadCategories() {
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<CategoryModel>()
                for (data in snapshot.children) {
                    val item = data.getValue(CategoryModel::class.java)
                    item?.let { list.add(it) }
                }
                _category.value = list
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun loadBanner() {
        val ref = firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<BannerModel>()
                for (data in snapshot.children) {
                    val item = data.getValue(BannerModel::class.java)
                    item?.let { list.add(it) }
                }
                _banner.value = list
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun loadItemsByCategory(categoryId: String): LiveData<MutableList<ItemModel>> {
        val itemsLiveData = MutableLiveData<MutableList<ItemModel>>()
        val ref = firebaseDatabase.getReference("Items")
        val query: Query = ref.orderByChild("categoryId").equalTo(categoryId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ItemModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ItemModel::class.java)
                    item?.let { list.add(it) }
                }
                itemsLiveData.value = list
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return itemsLiveData
    }
}