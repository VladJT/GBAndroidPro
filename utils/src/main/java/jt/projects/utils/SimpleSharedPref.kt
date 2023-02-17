package jt.projects.utils

import android.content.SharedPreferences

enum class SETTINGS { WORD, THEME }

class SimpleSharedPref(
    private val sharedPreferences: SharedPreferences
) {
    fun save(key: SETTINGS, value: String) {
        sharedPreferences.edit().putString(key.name, value).apply()
    }

    fun get(key: SETTINGS): String {
        return sharedPreferences.getString(key.name, "") ?: ""
    }
}