package com.example.mviapp.data.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mviapp.data.models.User
import com.example.mviapp.data.repositories.UsersRepository
import kotlinx.coroutines.*

class MainViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private var coroutineJob: Job? = null

    val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>()
    }

    init {
        getUsers()
    }

    /**
     * Gets all users
     */
    fun getUsers() {
        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            val response = usersRepository.getUsers()

            withContext(Dispatchers.Main) {
                if (response?.isSuccessful) {
                    users.value = response.body()
                }
            }
        }
    }

    /**
     * Gets users that match the search query
     */
    fun getUsers(query: String) {
        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            val response = usersRepository.getUsers(query)

            withContext(Dispatchers.Main) {
                if (response?.isSuccessful) {
                    users.value = response.body()
                }
            }
        }
    }

    override fun onCleared() {
        coroutineJob?.cancel()
        super.onCleared()
    }
}