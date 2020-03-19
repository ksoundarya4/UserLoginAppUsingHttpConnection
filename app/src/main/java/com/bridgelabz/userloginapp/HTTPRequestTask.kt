package com.bridgelabz.userloginapp

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class HTTPRequestTask : AsyncTask<Void, Void, Void>() {

    private var urlConnection: HttpURLConnection? = null
    private val tag = "LoginActivity"

    override fun doInBackground(vararg params: Void?): Void? {

        try {
            val url =
                URL("http://fundoonotes.incubation.bridgelabz.com/api/user?access_token=faUiQVguZu67f4ChfyOVOpfCUNr4lF8jeBTyZjGeNsOGRb8N32KD97QjB1IMXLhL")
            urlConnection = url.openConnection() as HttpURLConnection
//            urlConnection!!.addRequestProperty("id", "5e71aee0ad53b700227c5905")
            val responseCode = urlConnection!!.responseCode

            if (responseCode != 200) {
                throw IOException("Invalid response from Sever $responseCode")
            }

            val bufferedReader = BufferedReader(InputStreamReader(urlConnection!!.inputStream))

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                Log.i(tag, line!!)
//                Log.d(tag, line.toString())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            if (urlConnection != null)
                urlConnection!!.disconnect()
        }

        return null
    }
}