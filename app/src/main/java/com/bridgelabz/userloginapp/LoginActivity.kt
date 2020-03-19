package com.bridgelabz.userloginapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout

import com.google.android.material.snackbar.Snackbar

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

    private val loginLayout by lazy {
        findViewById<ConstraintLayout>(R.id.login_constaint_layout)
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
            val userLogin = UserLogin(email, password)
            if (isNetworkAvailable()) {
                httpRequestCall = HTTPRequestTask(userLogin)
                httpRequestCall.execute()
            } else
                Snackbar.make(loginLayout, "No Internet Connection", Snackbar.LENGTH_LONG).apply {
                    show()
                }
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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}