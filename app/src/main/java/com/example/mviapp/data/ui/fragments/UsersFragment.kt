package com.example.mviapp.data.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mviapp.R
import com.example.mviapp.data.repositories.UsersRepository
import com.example.mviapp.data.ui.adapters.UsersListAdapter
import com.example.mviapp.data.ui.factories.MainViewModelFactory
import com.example.mviapp.data.ui.viewmodels.MainViewModel
import com.example.mviapp.databinding.UsersFragmentBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersFragment : Fragment(),
    UsersListAdapter.OnUserSelectedCallback,
    SearchView.OnQueryTextListener {

    companion object {
        fun newInstance() = UsersFragment()
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://5f3577fe5b91f60016ca4dcf.mockapi.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val usersRepository = retrofit.create(UsersRepository::class.java)

    private val mainViewModel = MainViewModelFactory(usersRepository).create(MainViewModel::class.java)
    private lateinit var adapter: UsersListAdapter

    //region Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: UsersFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.users_fragment, container, false)

        val usersRecyclerView = binding.usersListView

        mainViewModel.users.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.users = it
            }
        })

        adapter = UsersListAdapter(this)
        usersRecyclerView.adapter = adapter
        usersRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
    //endregion

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchMenu = menu.findItem(R.id.search_bar)
        val searchBar = searchMenu.actionView as SearchView
        searchBar.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.search_bar -> Log.d("USERS_FRAGMENT", "Search icon clicked")
        }

        return true
    }
    //endregion

    //region OnUserSelectedCallback
    override fun onUserSelected(id: Int){
        val action = UsersFragmentDirections.actionUsersFragmentToUserFragment(id)
        NavHostFragment.findNavController(this).navigate(action)
    }
    //endregion

    //region OnQueryTextListener
    override fun onQueryTextSubmit(query: String?): Boolean {
        mainViewModel.getUsers(query ?: "")

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if (newText.isNullOrBlank())
            mainViewModel.getUsers()

        return true
    }
    //endregion
}