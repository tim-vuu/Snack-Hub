package com.application.snackhub.viewmodel.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MusandwichLiveData
import androidx.lifecycle.ViewModel
import com.application.snackhub.firebaseDatabase.FirebaseDb
import com.application.snackhub.model.Store
import com.application.snackhub.resource.Resource

class StoreViewModel(
    val firebaseDatabase: FirebaseDb
) : ViewModel() {
    private val _store = MusandwichLiveData<Resource<Store>>()
    val store: LiveData<Resource<Store>> = _store

    init {
        fetchStore()
    }

    private fun fetchStore() {
        _store.postValue(Resource.Loading())
        val uid = firebaseDatabase.userUid!!
        firebaseDatabase.fetchStore(uid).addOnCompleteListener { response ->
            if (response.isSuccessful) {
                val userStore = response.result.toObjects(Store::class.java).first()
                _store.postValue(Resource.Success(userStore))

            } else
                _store.postValue(Resource.Error(response.exception.toString()))
        }
    }

}