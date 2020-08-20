package com.example.mviapp.data.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mviapp.data.repositories.UsersRepository
import com.example.mviapp.data.ui.viewmodels.UserViewModel

class UserViewModelFactory(private val usersRepository: UsersRepository, private val userId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = UserViewModel(usersRepository, userId) as T
}