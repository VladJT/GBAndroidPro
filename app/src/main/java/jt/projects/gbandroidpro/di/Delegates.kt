package jt.projects.gbandroidpro.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import jt.projects.utils.SETTINGS
import jt.projects.utils.SimpleSharedPref
import org.koin.java.KoinJavaComponent.getKoin


fun Activity.saveWordToSharedPref(word: String) {
    getKoin().get<SimpleSharedPref>().save(SETTINGS.WORD, word)
}

fun ViewModel.saveWordToSharedPref(word: String) {
    getKoin().get<SimpleSharedPref>().save(SETTINGS.WORD, word)
}

fun Activity.getWordFromSharedPref(): String {
    return getKoin().get<SimpleSharedPref>().get(SETTINGS.WORD)
}