package com.example.mviapp.data.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mviapp.data.models.User
import com.example.mviapp.data.repositories.UsersRepository
import kotlinx.coroutines.*

class UserViewModel(private val usersRepository: UsersRepository, private val userId: Int) : ViewModel() {

    private var coroutineJob: Job? = null

    val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    init {
        getUser()
    }

    private fun getUser() {
        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            val response = usersRepository.getUser(userId)

            withContext(Dispatchers.Main) {
                if (response?.isSuccessful) {
                    user.value = response.body()
                }
            }
        }
    }

    override fun onCleared() {
        coroutineJob?.cancel()
        super.onCleared()
    }
}