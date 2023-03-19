package jt.projects.gbandroidpro

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import jt.projects.gbandroidpro.presentation.ui.history.HistoryActivity
import jt.projects.gbandroidpro.presentation.ui.history.HistoryAdapter
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * десь стоит уделить немного внимания классу ActivityScenario. Это класс тестового
фреймворка, умеет запускать нужные вам Активити без доступа к Контексту, так как разработчик в
принципе не может самостоятельно запустить Активити, а только создает Интент и перенаправляет
его ОС. Помимо этого ActivityScenario предоставляет вам возможность управлять жизненным циклом
Активити, что помогает во многих сценариях тестирования. Например, метод moveToState(State)
позволяет переводить нужную Активити между состояниями и проверять корректность ее работы.
 */

@RunWith(AndroidJUnit4::class)
class HistoryActivityEspressoTest {

    private lateinit var context: Context
    private lateinit var scenario: ActivityScenario<HistoryActivity>

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        scenario = ActivityScenario.launch(HistoryActivity::class.java)
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

    // isDisplayed() вернет true если хотя бы часть View отображается на экране
    @Test
    fun historyRecyclerView_IsDisplayed() {
        onView(withId(R.id.history_activity_recyclerview)).check(
            matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )
    }

    // проверка, что фрейм загрузки по умолчанию не отображается
    @Test
    fun loadingLayout_NotVisible() {
        onView(withId(R.id.history_loading_frame_layout)).check(
            matches(
                not(isCompletelyDisplayed())
            )
        )
    }

    // проверка, что по нажатию меню "Очистить историю" - вызывается диалоговое окно
    @Test
    fun buttonClearHistory_IsWorking() {
        onView(withId(R.id.menu_clean_history)).perform(ViewActions.click())
        onView(withText(R.string.dialog_clean_history_message)).check(matches(isDisplayed()))
    }

    // проверка, что работает скроллинг в списке слов в HistoryRecyclerView
    @Test
    fun historyRecyclerView_testScrolling() {
        onView(withId(R.id.history_activity_recyclerview)).perform(
            RecyclerViewActions.scrollToLastPosition<HistoryAdapter.RecyclerItemViewHolder>()
        )
    }

    // проверка, что по нажатию на первое найденное слово - вызывается DescriptionActivity
    @Test
    fun historyRecyclerView_testOnClickShowDescription() {
        onView(withId(R.id.history_activity_recyclerview))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HistoryAdapter.RecyclerItemViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        onView(withId(R.id.description_header)).check(matches(isDisplayed()))
    }
}