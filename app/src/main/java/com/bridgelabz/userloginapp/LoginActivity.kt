package com.bridgelabz.userloginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    private val emailEditText by lazy {
        findViewById<EditText>(R.id.login_email)
    }

    private val passwordEditText by lazy {
        findViewById<EditText>(R.id.login_password)
    }

    private val loginButton by lazy {
        findViewById<Button>(R.id.button_login)
    }

    private val registrationButton by lazy {
        findViewById<Button>(R.id.button_register)
    }

    private lateinit var httpRequestCall: HTTPRequestTask
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setButtonClickListeners()
    }

    private fun setButtonClickListeners() {
        setLoginButtonListener()
        setRegistrationButtonListener()
    }


    private fun setLoginButtonListener() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            httpRequestCall = HTTPRequestTask()
            httpRequestCall.execute()
        }
    }

    private fun setRegistrationButtonListener() {
        registrationButton.setOnClickListener {
            navigateToReciterActivity()
        }
    }

    private fun navigateToReciterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}