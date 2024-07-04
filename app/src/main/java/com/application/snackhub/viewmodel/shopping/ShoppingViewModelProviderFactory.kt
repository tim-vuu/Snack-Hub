package com.application.snackhub.viewmodel.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.snackhub.firebaseDatabase.FirebaseDb

class ShoppingViewModelProviderFactory(
    val db:FirebaseDb
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShoppingViewModel(db) as T
    }
}