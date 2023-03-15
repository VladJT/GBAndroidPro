package jt.projects.gbandroidpro.recordedTests

import android.view.View
import android.view.ViewGroup
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
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
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
                ViewMatchers.withId(R.id.search_edit_text),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.search_input_layout),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText.perform(ViewActions.replaceText("go"), ViewActions.closeSoftKeyboard())
        Thread.sleep(1500)

        textInputEditText.perform(ViewActions.replaceText("luck"), ViewActions.closeSoftKeyboard())
        Thread.sleep(2000)

        val recyclerView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.main_activity_recyclerview),
                childAtPosition(
                    ViewMatchers.withId(R.id.success_linear_layout),
                    1
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                ViewActions.click()
            )
        )
        delay(2000)

        Espresso.onView(ViewMatchers.withId(R.id.button_sound))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.description_header))
            .check(ViewAssertions.matches(ViewMatchers.withText("lucky you")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
