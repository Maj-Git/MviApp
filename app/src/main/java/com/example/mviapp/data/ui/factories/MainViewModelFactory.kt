package com.example.mviapp.data.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mviapp.data.repositories.UsersRepository
import com.example.mviapp.data.ui.viewmodels.MainViewModel

class MainViewModelFactory(private val usersRepository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(usersRepository) as T
}