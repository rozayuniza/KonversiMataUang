package com.android.konversimatauangasing.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.konversimatauangasing.R
import com.android.konversimatauangasing.model.ConversionModel
import com.android.konversimatauangasing.util.Constant
import kotlinx.android.synthetic.main.activity_conversion.*
import java.text.DecimalFormat

class ConversionActivity : AppCompatActivity() {

    private var type: String = ""
    private var moneyInput: String = ""
    lateinit var conversionData: ConversionModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)

        if (intent.extras != null && intent.extras!!.containsKey(Constant.Extras.EXTRAS_CONVERSION_TYPE)) {
            type = intent.extras!!.getString(Constant.Extras.EXTRAS_CONVERSION_TYPE)!!
        }

        if (intent.extras != null && intent.extras!!.containsKey(Constant.Extras.EXTRAS_CONVERSION_TYPE)) {
            conversionData = intent.extras!!.getParcelable(Constant.Extras.EXTRAS_CONVERSION_DATA)!!
        }

        setUpCheckBox()
        btn_process.setOnClickListener {
            //            Toast.makeText(this, moneyInput, Toast.LENGTH_LONG).show()
            moneyInput = number_input.text.toString()
            calculation()
        }
    }

    private fun setUpCheckBox() {
        when {
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.USD -> {
                radio0.text = getString(R.string.rupiah_ke_dollar)
                radio1.text = getString(R.string.dollar_ke_rupiah)
            }
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.EUR -> {
                radio0.text = getString(R.string.rupiah_ke_euro)
                radio1.text = getString(R.string.euro_ke_rupiah)
            }
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.GBP -> {
                radio0.text = getString(R.string.rupiah_ke_pounds)
                radio1.text = getString(R.string.pounds_ke_rupiah)
            }
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.JPY -> {
                radio0.text = getString(R.string.rupiah_ke_yen)
                radio1.text = getString(R.string.yen_ke_rupiah)
            }
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.MYR -> {
                radio0.text = getString(R.string.rupiah_ke_ringgit)
                radio1.text = getString(R.string.ringgit_ke_rupiah)
            }
        }
    }

    private fun calculation() {
        val rp = DecimalFormat("Rp###,###,###,###,###.##")
        val usd = DecimalFormat("$###,###,###,###,###.#######")
        val eur = DecimalFormat("€###,###,###,###,###.#######")
        val gbp = DecimalFormat("£###,###,###,###,###.#######")
        val jpy = DecimalFormat("¥###,###,###,###,###.###")
        val myr = DecimalFormat("RM ###,###,###,###,###.#######")

        val message: String
        val hasil: Double

        when {
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.USD -> if (radio0.isChecked) {
                hasil = moneyInput.toDouble() * conversionData.usd!!
                message = "${rp.format(moneyInput.toDouble())} = ${usd.format(hasil)}"
            } else {
                hasil = moneyInput.toDouble() / conversionData.usd!!
                message = "${usd.format(moneyInput.toDouble())} = ${rp.format(hasil)}"
            }
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.EUR -> if (radio0.isChecked) {
                hasil = moneyInput.toDouble() * conversionData.eur!!
                message = "${rp.format(moneyInput.toDouble())} = ${eur.format(hasil)}"
            } else {
                hasil = moneyInput.toDouble() / conversionData.eur!!
                message = "${eur.format(moneyInput.toDouble())} = ${rp.format(hasil)}"
            }
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.GBP -> if (radio0.isChecked) {
                hasil = moneyInput.toDouble() * conversionData.gbp!!
                message = "${rp.format(moneyInput.toDouble())} = ${gbp.format(hasil)}"
            } else {
                hasil = moneyInput.toDouble() / conversionData.gbp!!
                message = "${gbp.format(moneyInput.toDouble())} = ${rp.format(hasil)}"
            }
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.JPY -> if (radio0.isChecked) {
                hasil = moneyInput.toDouble() * conversionData.jpy!!
                message = "${rp.format(moneyInput.toDouble())} = ${jpy.format(hasil)}"
            } else {
                hasil = moneyInput.toDouble() / conversionData.jpy!!
                message = "${jpy.format(moneyInput.toDouble())} = ${rp.format(hasil)}"
            }
            Constant.MoneyConversion.getEnum(type) == Constant.MoneyConversion.MYR -> if (radio0.isChecked) {
                hasil = moneyInput.toDouble() * conversionData.myr!!
                message = "${rp.format(moneyInput.toDouble())} = ${myr.format(hasil)}"
            } else {
                hasil = moneyInput.toDouble() / conversionData.myr!!
                message = "${myr.format(moneyInput.toDouble())} = ${rp.format(hasil)}"
            }
            else -> {
                message = "NOT FOUND"
            }
        }

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Hasil")
        dialog.setMessage(message)
        dialog.setPositiveButton(
            "OK"
        ) { dialog, which -> dialog.dismiss() }
        dialog.create()
        dialog.show()
    }
}