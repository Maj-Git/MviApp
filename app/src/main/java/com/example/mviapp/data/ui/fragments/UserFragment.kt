package com.example.mviapp.data.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.mviapp.R
import com.example.mviapp.data.repositories.UsersRepository
import com.example.mviapp.data.ui.factories.MainViewModelFactory
import com.example.mviapp.data.ui.factories.UserViewModelFactory
import com.example.mviapp.data.ui.viewmodels.MainViewModel
import com.example.mviapp.data.ui.viewmodels.UserViewModel
import com.example.mviapp.databinding.UserFragmentBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserFragment : Fragment() {

    val args: UserFragmentArgs by navArgs()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://5f3577fe5b91f60016ca4dcf.mockapi.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val usersRepository = retrofit.create(UsersRepository::class.java)

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: UserFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false)

       userViewModel = UserViewModelFactory(usersRepository, args.userId)
           .create(UserViewModel::class.java)

        userViewModel.user.observe(viewLifecycleOwner, Observer {
            binding.user = it
        })

        return binding.root
    }
}