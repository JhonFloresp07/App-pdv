package com.e.mytodowithsqlite.activities


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

class UsersListActivity : AppCompatActivity() {
    private val activity = this@UsersListActivity
    private lateinit var textViewName: TextView
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var listUsers: ArrayList<UserModel>
    private lateinit var usersRecyclerAdapter: UsersRecyclerAdapter
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
        recyclerViewUsers = findViewById<RecyclerView>(R.id.recyclerViewUsers)
    }
    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        listUsers = ArrayList()
        usersRecyclerAdapter = UsersRecyclerAdapter(listUsers)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager = mLayoutManager
        recyclerViewUsers.itemAnimator = DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.adapter = usersRecyclerAdapter
        databaseHelper = DBHelper(activity)
        val emailFromIntent = intent.getStringExtra("EMAIL")
        textViewName.text = emailFromIntent
        //var getDataFromSQLite = GetDataFromSQLite()
        //getDataFromSQLite.execute()
        viewCustomers()
    }


    private fun viewCustomers(){
        listUsers = databaseHelper.getAllUser()
        val adapter = UsersRecyclerAdapter(listUsers)
        val rv: RecyclerView = findViewById(R.id.recyclerViewUsers)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }


    /**
     * This class is to fetch all user records from SQLite
     */
    /*inner class GetDataFromSQLite : AsyncTask<Void, Void, ArrayList<UserModel>>() {
        override fun doInBackground(vararg p0: Void?): ArrayList<UserModel> {
            return databaseHelper.getAllUser()
        }
        override fun onPostExecute(result: ArrayList<UserModel>?) {
            super.onPostExecute(result)
            listUsers.clear()
            listUsers.addAll(result!!)
        }
    }*/
}