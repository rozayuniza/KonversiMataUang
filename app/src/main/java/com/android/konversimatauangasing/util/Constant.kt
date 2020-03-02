package com.android.konversimatauangasing.util

class Constant {

    object Extras {
        const val EXTRAS_CONVERSION_DATA = "extras_conversion_data"
        const val EXTRAS_CONVERSION_TYPE = "extras_conversion_type"
    }

    enum class MoneyConversion(val value: String) {
        DEFAULT(""),
        USD("usd"),
        EUR("eur"),
        GBP("gbp"),
        MYR("myr"),
        JPY("jpy");

        companion object {
            fun getEnum(name: String): MoneyConversion {
                for (enum in values()) {
                    if (enum.value == name) {
                        return enum
                    }
                }
                return DEFAULT
            }
        }
    }
}