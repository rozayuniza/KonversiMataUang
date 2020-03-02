package com.android.konversimatauangasing.network

import android.os.AsyncTask
import android.util.Log
import com.android.konversimatauangasing.`interface`.OnGetDataComplete
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK, IDLE, NOT_INITIALIZED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

class GetRawData constructor(private var listener: OnGetDataComplete) : AsyncTask<String, Void, String>() {

    private var TAG = "GetRawData"
    private var downloadStatus = DownloadStatus.IDLE

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        Log.d(TAG, "onPostExecute called, parameter is $result")
        listener.onGetDataComplete(result, downloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            downloadStatus =
                DownloadStatus.NOT_INITIALIZED
            return "No URL Specified"
        }

        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> {
                    downloadStatus =
                        DownloadStatus.NOT_INITIALIZED
                    "doInBackground: Invalid URL ${e.message}"
                }
                is IOException -> {
                    downloadStatus =
                        DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground: IO Exception reading data: ${e.message}"
                }
                is SecurityException -> {
                    downloadStatus =
                        DownloadStatus.PERMISSIONS_ERROR
                    "doInBackground: Security exception: Need Permission? ${e.message}"
                }
                else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "Unknown error: ${e.message}"
                }
            }
            Log.e(TAG, errorMessage)

            return errorMessage
        }
    }
}