package com.example.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val dao: UserDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MyViewModel::class.java) ->
                MyViewModel(dao) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
