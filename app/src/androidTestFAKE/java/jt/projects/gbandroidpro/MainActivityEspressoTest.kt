package jt.projects.gbandroidpro

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import jt.projects.gbandroidpro.presentation.ui.main.MainActivity
import jt.projects.utils.FAKE
import jt.projects.utils.REAL
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTestFake {

    private lateinit var context: Context
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun searchWord_IsWorking() {
        val someWord = "go"
        onView(withId(R.id.search_edit_text)).perform(ViewActions.click())
        onView(withId(R.id.search_edit_text)).perform(ViewActions.replaceText(someWord))
        onView(withId(R.id.search_edit_text)).perform(ViewActions.closeSoftKeyboard())

        scenario.onActivity {
            assertEquals(1, it.lastResultCount)
        }
    }

}