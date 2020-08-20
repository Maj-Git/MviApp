package com.example.mviapp.data.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mviapp.R
import com.example.mviapp.data.models.User
import com.example.mviapp.databinding.UserItemBinding

class UsersListAdapter(private val context: OnUserSelectedCallback) : RecyclerView.Adapter<UsersListAdapter.ViewHolder>(){

    interface OnUserSelectedCallback {
        fun onUserSelected(id: Int)
    }

     var users = listOf<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(inflater)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(users[position])

    inner class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            binding.user = user

            binding.root.setOnClickListener {
                context.onUserSelected(user.id)
            }

            binding.executePendingBindings()
        }
    }

    inner class UsersDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem
    }
}