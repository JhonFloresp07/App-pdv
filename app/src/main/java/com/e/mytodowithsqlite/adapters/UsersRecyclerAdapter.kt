package com.e.mytodowithsqlite.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.e.mytodowithsqlite.R
import com.e.mytodowithsqlite.model.UserModel

class UsersRecyclerAdapter(private val listUsers: ArrayList<UserModel>) : RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_recycler, parent, false)
        return UserViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.textViewName.text = listUsers[position].name
        holder.textViewEmail.text = listUsers[position].email
        holder.textViewPassword.text = listUsers[position].password
    }
    override fun getItemCount(): Int {
        return listUsers.size
    }
    /**
     * ViewHolder class
     */
    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewName: TextView
        var textViewEmail: TextView
        var textViewPassword: TextView
        var popupMenu: TextView
        init {
            textViewName = view.findViewById(R.id.textViewName) as TextView
            textViewEmail = view.findViewById(R.id.textViewEmail) as TextView
            textViewPassword = view.findViewById(R.id.textViewPassword) as TextView
            popupMenu = view.findViewById(R.id.textViewOptions) as TextView
        }
    }
}