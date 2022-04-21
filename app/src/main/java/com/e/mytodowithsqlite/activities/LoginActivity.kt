package com.e.mytodowithsqlite.activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e.mytodowithsqlite.R
import com.e.mytodowithsqlite.dbmanager.DBHelper
import com.e.mytodowithsqlite.utils.InputValidation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_users_list.*


class LoginActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DBHelper
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var inputValidation: InputValidation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // hiding the action bar
        supportActionBar!!.hide()

        initObjects()
    }

    fun checkUser(v: View) {
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
                textInputEditTextPassword,
                textInputLayoutPassword,
                getString(R.string.error_message_password)
            )
        ) {
            return
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.text.toString().trim())) {
            if(databaseHelper.checkUser(textInputEditTextPassword.text.toString().trim())){
                Toast.makeText(this, getString(R.string.login_success_message), Toast.LENGTH_LONG).show()
                //textViewName.getUserId = databaseHelper.getUserIdByEmail(textInputEditTextEmail.text.toString())
                val accountsIntent = Intent(this, FragmentHome::class.java)
                startActivity(accountsIntent)
                finish()
            }
            } else {
                Toast.makeText(this, "not exist", Toast.LENGTH_LONG).show()
            }
        }

        fun openRegister(v: View) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        private fun initObjects() {
            inputValidation = InputValidation(this)
            databaseHelper = DBHelper(this)
            textInputLayoutEmail = findViewById<TextInputLayout>(R.id.textInputLayoutEmail)
            textInputLayoutPassword = findViewById<TextInputLayout>(R.id.textInputLayoutPassword)
            textInputEditTextEmail = findViewById<TextInputEditText>(R.id.textInputEditTextEmail)
            textInputEditTextPassword = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)
        }
}