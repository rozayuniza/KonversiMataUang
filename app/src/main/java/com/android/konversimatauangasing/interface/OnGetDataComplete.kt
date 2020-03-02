package com.android.konversimatauangasing.`interface`

import com.android.konversimatauangasing.network.DownloadStatus

interface OnGetDataComplete {
    fun onGetDataComplete(data: String, status: DownloadStatus)
}