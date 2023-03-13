package jt.projects.gbandroidpro.presentation.ui.main


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.presentation.ui.history.HistoryActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HistoryActivityEspressoAutoTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(HistoryActivity::class.java)

    @Test
    fun historyActivityEspressoAutoTest() {


        onView(withId(R.id.menu_clean_history)).perform(click())

        onView(withText(R.string.dialog_clean_history_message)).check(matches(isDisplayed()))

    }


}
