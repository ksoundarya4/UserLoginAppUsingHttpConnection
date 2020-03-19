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

        //GET
        try {
            val url =
                URL("http://fundoonotes.incubation.bridgelabz.com/api/user/5e71aee0ad53b700227c5905?access_token=faUiQVguZu67f4ChfyOVOpfCUNr4lF8jeBTyZjGeNsOGRb8N32KD97QjB1IMXLhL")
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

        //POST
        try {

            val postData = JsonObject()
            postData.addProperty("email", "ksoundarya4@gmail.com")
            postData.addProperty("password", "sound7")

            val url =
                URL("http://fundoonotes.incubation.bridgelabz.com/api/user/login?access_token=rDC8Uhh0TL5fbEhu12pRe1Gxr30r4NutroMsDmuMEyPOhp8wu3cEmbQeAKqyNCCb")
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection!!.setRequestProperty("Content-Type", "application/json")
            urlConnection!!.requestMethod = "POST"
            urlConnection!!.doOutput = true
            urlConnection!!.doInput = true
            urlConnection!!.setChunkedStreamingMode(0)

            val outputStream = BufferedOutputStream(urlConnection!!.outputStream)
            val writer = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
            writer.write(postData.toString())
            writer.flush()

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