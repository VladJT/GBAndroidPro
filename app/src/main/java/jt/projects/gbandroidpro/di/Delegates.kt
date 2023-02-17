package jt.projects.gbandroidpro.di

import android.app.Activity
import jt.projects.utils.SharedPref
import org.koin.core.component.KoinComponent

object ShPref : KoinComponent {
    private val sharedPref = getKoin().get<SharedPref>()

    fun saveWord(word: String) {
        sharedPref.settings.currentWord = word
        sharedPref.saveSettings()
    }

    fun getWord(): String {
        return sharedPref.settings.currentWord
    }
}

fun Activity.saveWordToShPref(word: String) = ShPref.saveWord(word)

fun Activity.getWordFromSharedPref(): String = ShPref.getWord()