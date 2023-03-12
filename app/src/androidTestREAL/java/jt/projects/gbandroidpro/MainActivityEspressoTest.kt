package jt.projects.gbandroidpro

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import jt.projects.gbandroidpro.presentation.ui.history.HistoryActivity
import jt.projects.gbandroidpro.presentation.ui.main.MainActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
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
    fun loadingLayout_NotVisible() {
        onView(withId(R.id.main_loading_frame_layout)).check(
            ViewAssertions.matches(
                Matchers.not(ViewMatchers.isCompletelyDisplayed())
            )
        )
    }

    @Test
    fun searchWord_IsWorking() {
        val wordKey = "go"

        onView(withId(R.id.search_edit_text))
            .perform(ViewActions.click())
            .perform(ViewActions.replaceText(wordKey))
            .perform(ViewActions.closeSoftKeyboard())

        onView(isRoot()).perform(delay(2000))
        scenario.onActivity {
            assertEquals(expectedData[wordKey]!!.getMeaningsCount(), it.lastResultCount)
        }
    }

    // по нажатию меню "Показать историю" - вызывается HistoryActivity
    @Test
    fun menuStartHistoryActivity_IsWorking() {
        Intents.init()
        onView(withId(R.id.menu_history)).perform(ViewActions.click())
        intended(hasComponent(HistoryActivity::class.java.name))
    }
}