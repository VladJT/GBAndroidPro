package jt.projects.utils

import android.content.SharedPreferences

object SpConstants {
    const val WORD = "WORD"
    const val COMMENT = ""
}

class SimpleSharedPref(
    private val sharedPreferences: SharedPreferences
) {
    fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun get(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }
}