package jt.projects.gbandroidpro.presentation.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import jt.projects.gbandroidpro.R
import jt.projects.tests.delay
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailsActivityEspressoAutoTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun detailsActivityEspressoAutoTest() {
        onView(withId(R.id.search_edit_text))
            .perform(replaceText("go"), closeSoftKeyboard())

        delay(2000)

        val textInputEditText = onView(
            allOf(
                withId(R.id.search_edit_text),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.search_input_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("luck"), closeSoftKeyboard())

        delay(2000)

        val recyclerView = onView(
            allOf(
                withId(R.id.main_activity_recyclerview),
                childAtPosition(
                    withId(R.id.success_linear_layout),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(3, click()))

        delay(2000)

        onView(withId(R.id.button_sound)).check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.description_header), withText("lucky you"),
            )
        )
        textView.check(matches(withText("lucky you")))
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
