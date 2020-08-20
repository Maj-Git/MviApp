package com.example.mviapp.data.repositories

import androidx.lifecycle.LiveData
import com.example.mviapp.data.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersRepository {
    @GET("/users")
    suspend fun getUsers(@Query("search") query: String? = null): Response<List<User>>

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>
}