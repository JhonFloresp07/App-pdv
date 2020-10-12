package com.e.mytodowithsqlite.activities


import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.mytodowithsqlite.R
import com.e.mytodowithsqlite.adapters.UsersRecyclerAdapter
import com.e.mytodowithsqlite.dbmanager.DBHelper
import com.e.mytodowithsqlite.model.UserModel
import kotlinx.android.synthetic.main.activity_login.*

class UsersListActivity : AppCompatActivity() {

    private val activity = this@UsersListActivity
    private lateinit var textViewName: TextView
    private lateinit var rv: RecyclerView
    private lateinit var listUsers: ArrayList<UserModel>
    private lateinit var adapter: UsersRecyclerAdapter
    private lateinit var databaseHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        supportActionBar!!.hide()
        initViews()
        initObjects()

    }
    /**
     * This method is to initialize views
     */
    private fun initViews() {
        textViewName = findViewById<TextView>(R.id.textViewName)
    }
    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        databaseHelper = DBHelper(activity)
        viewCustomers()

    }


    private fun viewCustomers(){
        listUsers = databaseHelper.getAllUser()
        adapter = UsersRecyclerAdapter(this, listUsers)
        rv = findViewById(R.id.recyclerViewUsers)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,
            false) as RecyclerView.LayoutManager

        rv.itemAnimator = DefaultItemAnimator()
        rv.setHasFixedSize(true)

        rv.adapter = adapter
    }
}