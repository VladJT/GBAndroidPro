package jt.projects.gbandroidpro.recordedTests

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.presentation.ui.history.HistoryActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class RecordedHistoryActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(HistoryActivity::class.java)

    @Test
    fun historyActivityEspressoAutoTest() {

        Espresso.onView(ViewMatchers.withId(R.id.menu_clean_history)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.dialog_clean_history_message))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}