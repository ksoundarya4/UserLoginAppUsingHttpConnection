package com.bridgelabz.userloginapp

import android.os.AsyncTask
import android.util.Log
import com.google.gson.JsonObject
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class HTTPRequestTask : AsyncTask<Void, Void, Void>() {

    private var urlConnection: HttpURLConnection? = null
    private val tag = "LoginActivity"

    override fun doInBackground(vararg params: Void?): Void? {

        try {

            performGETUsers()
            performPOSTUserLogin()

        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            if (urlConnection != null)
                urlConnection!!.disconnect()
        }
        return null
    }

    private fun performPOSTUserLogin() {

        val postData = JsonObject()
        postData.addProperty("email", "ksoundarya4@gmail.com")
        postData.addProperty("password", "sound7")

        val urlAddress = "http://fundoonotes.incubation.bridgelabz.com/api/user/login"
        urlConnection = getUrlConnection(urlAddress)

        val outputStream = BufferedOutputStream(urlConnection!!.outputStream)
        val writer = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
        writer.write(postData.toString())
        writer.flush()

        getHttpResponse(urlConnection!!)
    }

    private fun performGETUsers() {

        val urlAddress = "http://fundoonotes.incubation.bridgelabz.com/api/user"

        urlConnection = getUrlConnection(urlAddress)

        getHttpResponse(urlConnection!!)
    }

    private fun getHttpResponse(urlConnection: HttpURLConnection) {

        val responseCode = urlConnection.responseCode

        if (responseCode != 200) {
            throw IOException("Invalid response from Sever $responseCode")
        }

        val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            Log.i(tag, line!!)
        }
    }

    private fun getUrlConnection(urlAddress: String): HttpURLConnection {
        val url = URL(urlAddress)
        return url.openConnection() as HttpURLConnection
    }
}