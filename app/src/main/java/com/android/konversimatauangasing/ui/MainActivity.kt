package com.android.konversimatauangasing.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.konversimatauangasing.R
import com.android.konversimatauangasing.`interface`.OnGetDataComplete
import com.android.konversimatauangasing.model.ConversionModel
import com.android.konversimatauangasing.network.DownloadStatus
import com.android.konversimatauangasing.network.GetRawData
import com.android.konversimatauangasing.util.Constant
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity(),
    OnGetDataComplete {

    private val conversionData = ConversionModel()

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onGetDataComplete(data: String, status: DownloadStatus) {
        progress.visibility = View.GONE
        container_main.visibility = View.VISIBLE
        if (status == DownloadStatus.OK) {
            Log.d("MAIN", "onDownloadComplete, data id $data")
            val obj = JSONObject(data)
            val rates = obj.optJSONObject("rates")

            try {
                conversionData.idr = rates.optDouble("IDR")
                conversionData.usd = rates.optDouble("USD")
                conversionData.eur = rates.optDouble("EUR")
                conversionData.gbp = rates.optDouble("GBP")
                conversionData.myr = rates.optDouble("MYR")
                conversionData.jpy = rates.optDouble("JPY")
            } catch (e: Exception) {
                Toast.makeText(this, "Terjadi kesalahan pada data", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, data, Toast.LENGTH_LONG).show()
            Log.d("MAIN", "onDownloadComplete, failed with status $status. Error message is $data ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()

        button_usd.setOnClickListener {
            goToConversionPage("usd")
        }
        button_euro.setOnClickListener {
            goToConversionPage("eur")
        }
        button_gbp.setOnClickListener {
            goToConversionPage("gbp")
        }
        button_jpy.setOnClickListener {
            goToConversionPage("jpy")
        }
        button_myr.setOnClickListener {
            goToConversionPage("myr")
        }
    }

    private fun getData() {
        val getRawData = GetRawData(this)
        getRawData.execute("https://api.exchangeratesapi.io/latest?base=IDR")
        progress.visibility = View.VISIBLE
        container_main.visibility = View.GONE
    }

    private fun goToConversionPage(conversion: String) {
        val bundle = Bundle()
        bundle.putParcelable(Constant.Extras.EXTRAS_CONVERSION_DATA, conversionData)
        bundle.putString(Constant.Extras.EXTRAS_CONVERSION_TYPE, conversion)
        val mIntent = Intent(this, ConversionActivity::class.java)
        mIntent.putExtras(bundle)
        startActivity(mIntent)
    }
}
