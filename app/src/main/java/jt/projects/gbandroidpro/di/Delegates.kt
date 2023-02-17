package jt.projects.gbandroidpro.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import jt.projects.utils.SimpleSharedPref
import jt.projects.utils.SpConstants
import org.koin.java.KoinJavaComponent.getKoin


fun Activity.saveWordToSharedPref(word: String) {
    getKoin().get<SimpleSharedPref>().save(SpConstants.WORD, word)
}

fun ViewModel.saveWordToSharedPref(word: String) {
    getKoin().get<SimpleSharedPref>().save(SpConstants.WORD, word)
}

fun Activity.getWordFromSharedPref(): String {
    return getKoin().get<SimpleSharedPref>().get(SpConstants.WORD)
}