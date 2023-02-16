package jt.projects.gbandroidpro.di

import android.app.Activity
import jt.projects.utils.SharedPref
import org.koin.core.component.KoinComponent

object ShPref : KoinComponent {
    val sharedPref = getKoin().get<SharedPref>()

    fun saveWord(word: String) {
        sharedPref.settings.currentWord = word
        sharedPref.saveSettings()
    }

    fun getWord(): String {
        return sharedPref.settings.currentWord
    }
}

fun Activity.saveToShPref(word: String) = ShPref.saveWord(word)

fun Activity.getWordFromSharedPref(): String = ShPref.getWord()
//class MyLazy(i: () -> List<Int>) {
//    private var value = 0
//
//    init {
//        i.invoke().forEach {
//            value += it
//        }
//    }
//
//    operator fun getValue(thisRef: Nothing?, property: KProperty<*>): Any{
//        return "[$thisRef] ${property.name}, result SUM = $value"
//    }
//
//}