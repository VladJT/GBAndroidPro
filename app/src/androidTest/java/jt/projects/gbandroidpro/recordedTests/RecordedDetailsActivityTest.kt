package jt.projects.gbandroidpro.recordedTests

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.presentation.ui.main.MainActivity
import jt.projects.tests.delay
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RecordedDetailsActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun detailsActivityEspressoAutoTest() {
        val textInputEditText = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.search_edit_text)
            )
        )
        textInputEditText.perform(ViewActions.replaceText("go"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.isRoot()).perform(delay(2000))

        textInputEditText.perform(ViewActions.replaceText("luck"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.isRoot()).perform(delay(2000))

        val recyclerView = Espresso.onView(
            ViewMatchers.withId(R.id.main_activity_recyclerview)
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.isRoot()).perform(delay(2000))

        Espresso.onView(ViewMatchers.withId(R.id.button_sound))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.description_header))
            .check(ViewAssertions.matches(ViewMatchers.withText("lucky you")))
    }

}
