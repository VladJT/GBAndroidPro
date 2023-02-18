package jt.projects.utils.shared_preferences

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class SharedPref(private val sharedPreferences: SharedPreferences, private val dbKey: String) {
    private var _settings: Settings? = null

    val settings: Settings
        get() {
            if (_settings == null) loadSettings()
            return _settings!!
        }

    private fun loadSettings() {
        val jsonNotes = sharedPreferences.getString(dbKey, null)
        val type = object : TypeToken<Settings>() {}.type
        val data = GsonBuilder().create().fromJson<Any>(jsonNotes, type)
        _settings = if (data == null) {
            Settings()
        } else data as Settings
    }

    fun saveSettings() {
        var jsonNotes: String? = ""
        _settings?.let {
            jsonNotes = GsonBuilder().create().toJson(it)
        }
        sharedPreferences.edit().putString(dbKey, jsonNotes).apply()
    }
}

// класс хранения настроек в формате JSON
data class Settings(
//    var theme: Int = R.style.Theme_GBNasaApp, //default
    var currentWord: String = ""
)