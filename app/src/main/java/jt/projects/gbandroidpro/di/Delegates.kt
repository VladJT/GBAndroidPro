package jt.projects.gbandroidpro.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import jt.projects.utils.shared_preferences.SHARED_PREF_SETTINGS
import jt.projects.utils.shared_preferences.SimpleSharedPref
import org.koin.java.KoinJavaComponent.getKoin


fun Activity.saveWordToSharedPref(word: String) {
    getKoin().get<SimpleSharedPref>().save(SHARED_PREF_SETTINGS.WORD, word)
}

fun ViewModel.saveWordToSharedPref(word: String) {
    getKoin().get<SimpleSharedPref>().save(SHARED_PREF_SETTINGS.WORD, word)
}

fun Activity.getWordFromSharedPref(): String {
    return getKoin().get<SimpleSharedPref>().get(SHARED_PREF_SETTINGS.WORD)
}