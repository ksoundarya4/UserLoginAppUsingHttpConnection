package com.bridgelabz.userloginapp

import android.os.AsyncTask
import android.util.Log
import com.google.gson.JsonObject
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class HTTPRequestTask(private val userLogin: UserLogin) : AsyncTask<Void, Void, Void>() {

    private var urlConnection: HttpURLConnection? = null
    private val tag = "LoginActivity"

    override fun doInBackground(vararg params: Void?): Void? {

        performGETUsers()
        performPOSTUserLogin()
        return null
    }

    private fun performPOSTUserLogin() {
        try {
            val postData = getUserLoginPostData(userLogin)

            val urlAddress =
                "http://fundoonotes.incubation.bridgelabz.com/api/user/login"
            urlConnection = getUrlConnection(urlAddress)
            urlConnection!!.setRequestProperty("Content-Type", "application/json")
            urlConnection!!.requestMethod = "POST"
            urlConnection!!.doOutput = true
            urlConnection!!.doInput = true
            urlConnection!!.setChunkedStreamingMode(0)

            val outputStream = BufferedOutputStream(urlConnection!!.outputStream)
            val writer = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
            writer.write(postData.toString())
            writer.flush()

            getHttpResponse(urlConnection!!)
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            if (urlConnection != null)
                urlConnection!!.disconnect()
        }
    }

    private fun performGETUsers() {
        try {
            val urlAddress = "http://fundoonotes.incubation.bridgelabz.com/api/user"

            urlConnection = getUrlConnection(urlAddress)
            getHttpResponse(urlConnection!!)

        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            if (urlConnection != null)
                urlConnection!!.disconnect()
        }
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

    private fun getUserLoginPostData(userLogin: UserLogin): JsonObject {
        val postData = JsonObject()
        postData.addProperty("email", userLogin.email)
        postData.addProperty("password", userLogin.password)
        return postData
    }
}