package jt.projects.gbandroidpro

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import jt.projects.gbandroidpro.presentation.ui.history.HistoryActivity
import jt.projects.gbandroidpro.presentation.ui.main.MainActivity
import jt.projects.gbandroidpro.presentation.ui.main.MainAdapter
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

    // проверка, что фрейм загрузки по умолчанию не отображается
    @Test
    fun loadingLayout_NotVisible() {
        onView(withId(R.id.main_loading_frame_layout)).check(
            ViewAssertions.matches(
                Matchers.not(ViewMatchers.isDisplayed())
            )
        )
    }

    // проверка, что работает поиск по крректному запросу слова
    @Test
    fun searchWord_IsWorking() {
        val wordKey = "go"
        searchWord(wordKey)

        scenario.onActivity {
            assertEquals(expectedData[wordKey]!!.getMeaningsCount(), it.lastResultCount)
        }
    }

    // проверка, что работает скроллинг в списке найденных слов
    @Test
    fun mainRecyclerView_testScrolling() {
        val wordKey = "cold"
        val searchKey = "cold hands warm heart"
        searchWord(wordKey)

        onView(withId(R.id.main_activity_recyclerview))
            .perform(
                RecyclerViewActions.scrollTo<MainAdapter.ViewHolder>(
                    hasDescendant(withText(searchKey))
                )
            )
            .check(matches(isDisplayed()))
    }

    // проверка, что по нажатию на первое найденное слово - вызывается DescriptionActivity
    @Test
    fun mainRecyclerView_testOnClickShowDescription() {
        val wordKey = "cool"
        searchWord(wordKey)

        onView(withId(R.id.main_activity_recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MainAdapter.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        onView(withId(R.id.description_header)).check(matches(withText(wordKey)))
    }

    // проверка, что по нажатию кнопки меню "Показать историю" - вызывается HistoryActivity
    @Test
    fun menuStartHistoryActivity_IsWorking() {
        Intents.init()
        onView(withId(R.id.menu_history)).perform(ViewActions.click())
        intended(hasComponent(HistoryActivity::class.java.name))
    }
}