package com.e.mytodowithsqlite.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.e.mytodowithsqlite.R
import com.e.mytodowithsqlite.dbmanager.DBHelper
import com.e.mytodowithsqlite.model.UserModel
import kotlinx.android.synthetic.main.user_update.view.*

class UsersRecyclerAdapter(mCtx: Context, private val listUsers: ArrayList<UserModel>) :
    RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {

//    companion object {
//        lateinit var dbHandler: DBHelper
//    }
    lateinit var usersDBHelper: DBHelper

    val mCtx = mCtx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        usersDBHelper = DBHelper(mCtx)

        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_recycler, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user: UserModel = listUsers[position]

        holder.textViewName.text = user.name
        holder.textViewEmail.text = user.email
        holder.textViewPassword.text = user.password

        holder.popupMenu.setOnClickListener {
            val popup = PopupMenu(mCtx, holder.popupMenu)
            popup.inflate(R.menu.menu)
            popup.setOnMenuItemClickListener { item ->

                when (item.getItemId()) {

                    R.id.popup_updat -> {
                        val inflater = LayoutInflater.from(mCtx)
                        val view = inflater.inflate(R.layout.user_update, null)

                        val txtUserName: TextView = view.findViewById(R.id.EditName)
                        val txtUserEmail: TextView = view.findViewById(R.id.editEmail)
                        val txtuserPassword: TextView = view.findViewById(R.id.editPassword)

                        txtUserName.text = user.name
                        txtUserEmail.text = user.email
                        txtuserPassword.text = user.password

                        val builder = AlertDialog.Builder(mCtx)
                            .setTitle("Update customer info")
                            .setView(view)
                            .setPositiveButton("Update") { dialogInterface, i ->
                                val isUpdate = usersDBHelper.updateUser(
                                    user.userid.toString(),
                                    view.EditName.text.toString(),
                                    view.editEmail.text.toString(),
                                    view.editPassword.text.toString()
                                )
                                if (isUpdate) {
                                    listUsers[position].name = view.EditName.text.toString()
                                    listUsers[position].email = view.editEmail.text.toString()
                                    listUsers[position].password = view.editPassword.text.toString()
                                    notifyDataSetChanged()
                                    Toast.makeText(mCtx, "Updated successfully", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    Toast.makeText(
                                        mCtx,
                                        "Error updating customer",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            .setNegativeButton("Cancel") { dialogInterface, i ->

                            }
                        val alert = builder.create()
                        alert.show()

                    }

                    R.id.popup_delete -> {
                        val userName: String = user.name
                        AlertDialog.Builder(mCtx)
                            .setTitle("Warning")
                            .setMessage("Are you sure you want to delete: $userName ?")
                            .setPositiveButton("Yes") { dialogInterface, i ->
                                if (usersDBHelper.deleteUser(user.userid.toString())) {
                                    listUsers.removeAt(position)
                                    notifyItemRemoved(position)
                                    notifyItemRangeChanged(position, listUsers.size)

                                    Toast.makeText(
                                        mCtx,
                                        "Customer $userName deleted",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(mCtx, "Error deleting user", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                            .setNegativeButton("No") { dialogInterface, i -> }
                            .setIcon(R.drawable.ic_warning)
                            .show()
                    }
                }
                true
            }
            popup.show()
        }
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