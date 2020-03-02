package com.android.konversimatauangasing.model

import android.os.Parcel
import android.os.Parcelable

data class ConversionModel(
    var idr: Double? = 0.0,
    var usd: Double? = 0.0,
    var eur: Double? = 0.0,
    var gbp: Double? = 0.0,
    var myr: Double? = 0.0,
    var jpy: Double? = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idr)
        parcel.writeValue(usd)
        parcel.writeValue(eur)
        parcel.writeValue(gbp)
        parcel.writeValue(myr)
        parcel.writeValue(jpy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConversionModel> {
        override fun createFromParcel(parcel: Parcel): ConversionModel {
            return ConversionModel(parcel)
        }

        override fun newArray(size: Int): Array<ConversionModel?> {
            return arrayOfNulls(size)
        }
    }
}