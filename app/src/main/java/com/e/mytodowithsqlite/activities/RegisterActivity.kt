package com.e.mytodowithsqlite.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e.mytodowithsqlite.R
import com.e.mytodowithsqlite.dbmanager.DBHelper
import com.e.mytodowithsqlite.model.UserModel
import com.e.mytodowithsqlite.utils.InputValidation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    companion object{
        lateinit var usersDBHelper: DBHelper
    }

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText

    private lateinit var inputValidation: InputValidation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        usersDBHelper = DBHelper(this)
        inputValidation = InputValidation(this)

        // hiding the action bar
        supportActionBar!!.hide()

        initViews()
    }

    /**
     * This method is to initialize views
     */
    private fun initViews() {

        textInputLayoutName = findViewById<TextInputLayout>(R.id.textInputLayoutName)
        textInputLayoutEmail = findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
        textInputLayoutPassword = findViewById<TextInputLayout>(R.id.textInputLayoutPassword)
        textInputEditTextName = findViewById<TextInputEditText>(R.id.textInputEditTextName)
        textInputEditTextEmail = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
        textInputEditTextPassword = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)

    }

    fun addUser(v: View) {
        if (!inputValidation.isInputEditTextFilled(
                textInputEditTextName,
                textInputLayoutName,
                getString(R.string.error_message_name)
            )
        ) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(
                textInputEditTextEmail,
                textInputLayoutEmail,
                getString(R.string.error_message_email)
            )
        ) {
            return
        }
        if (!inputValidation.isInputEditTextEmail(
                textInputEditTextEmail,
                textInputLayoutEmail,
                getString(R.string.error_message_email)
            )
        ) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(
                textInputEditTextPassword, textInputLayoutPassword, getString(
                    R.string.error_message_password
                )
            )
        ) {
            return
        }
        if (!usersDBHelper.checkUser(textInputEditTextEmail.text.toString().trim())) {
            usersDBHelper.insertUser(
                UserModel(
                    name = textInputEditTextName.text.toString().trim(),
                    email = textInputEditTextEmail.text.toString().trim(),
                    password = textInputEditTextPassword.text.toString().trim()
                )
            )

            //clear all edittext s
            this.textInputEditTextEmail.setText("")
            this.textInputEditTextName.setText("")
            this.textInputEditTextPassword.setText("")
            this.textview_result.text = "Added user"
            Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_LONG).show()

            //this.ll_entries.removeAllViews()
        } else {
            Toast.makeText(this, getString(R.string.error_email_exists), Toast.LENGTH_LONG).show()
        }

    }

    fun deleteUser() {
        var userid = this.textInputEditTextPassword.text.toString()
        val result = usersDBHelper.deleteUser(userid)
        this.textview_result.text = "Deleted user : " + result
        this.ll_entries.removeAllViews()
    }



    fun openLogin(v: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}