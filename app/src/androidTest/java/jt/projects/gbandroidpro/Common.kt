package jt.projects.gbandroidpro

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import jt.projects.tests.pause

fun searchWord(wordKey: String) {
    Espresso.onView(ViewMatchers.withId(R.id.search_edit_text))
        .perform(ViewActions.click())
        .perform(ViewActions.replaceText(wordKey))
        .perform(ViewActions.closeSoftKeyboard())

    pause(2000)
}