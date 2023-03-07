package jt.projects.gbandroidpro

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import jt.projects.gbandroidpro.di.*
import jt.projects.gbandroidpro.presentation.ui.history.HistoryActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.core.context.stopKoin
import org.koin.test.junit5.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.fakes.RoboMenuItem
import org.robolectric.shadows.ShadowDialog
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class HistoryActivityTest : AutoCloseKoinTest() {

    lateinit var scenario: ActivityScenario<HistoryActivity>
    private lateinit var context: Context

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(HistoryActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun tearDown() {
        scenario.close()
        if (GlobalContext.getOrNull() != null) {
            stopKoin()
        }
    }

    // проверка, что Activity корректно создается
    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    // По нажатию на кнопку "Очистить историю" - вызывается Dialog для подтверждения удаления
    @Test
    fun menuHistory_IsWorking() {
        scenario.onActivity { activity ->
            val menuItem = RoboMenuItem(R.id.menu_clean_history)
            activity.onOptionsItemSelected(menuItem)

            // без этого метода - ShadowDialog.getLatestDialog()=null
            activity.supportFragmentManager.executePendingTransactions()

            val alert = ShadowDialog.getLatestDialog()
            val shadow = Shadows.shadowOf(alert)

            assertNotNull(alert)
            //assertEquals(shadow.title, context.getString(R.string.dialog_clean_history_title))
        }
    }

}