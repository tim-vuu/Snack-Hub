package com.application.snackhub.viewmodel.lunchapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.snackhub.firebaseDatabase.FirebaseDb

class ViewModelProviderFactory(
    private val firebaseDb: FirebaseDb
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SnackHub.ViewModel(firebaseDb) as T
    }
}