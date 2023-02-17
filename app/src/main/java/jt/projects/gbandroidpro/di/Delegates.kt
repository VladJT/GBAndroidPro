package jt.projects.gbandroidpro.di

import android.app.Activity
import jt.projects.utils.SETTINGS
import jt.projects.utils.SimpleSharedPref
import org.koin.java.KoinJavaComponent.getKoin


fun Activity.saveWordToSharedPref(word: String) {
    getKoin().get<SimpleSharedPref>().save("WORD", word)
}

fun Activity.getWordFromSharedPref(): String {
    return getKoin().get<SimpleSharedPref>().get("WORD")
}