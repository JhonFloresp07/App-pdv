package com.e.mytodowithsqlite.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.e.mytodowithsqlite.R
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : AppCompatActivity() {

    private lateinit var toogle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        toogle = ActionBarDrawerToggle(this, drawerlayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerlayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_pdv ->{
                    val accountsIntent = Intent(this, UsersListActivity::class.java)
                    startActivity(accountsIntent)
                    finish()
                    }
                R.id.nav_signoff -> {
                    val accountsIntent = Intent(this, LoginActivity ::class.java)
                    startActivity(accountsIntent)
                    finish()
                }
            }
            drawerlayout.closeDrawer(GravityCompat.START)

            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
