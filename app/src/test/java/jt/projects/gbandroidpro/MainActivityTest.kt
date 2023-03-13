package jt.projects.gbandroidpro

import android.view.inputmethod.EditorInfo
import androidx.test.core.app.ActivityScenario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import jt.projects.gbandroidpro.di.*
import jt.projects.gbandroidpro.presentation.ui.main.MainActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.core.context.stopKoin
import org.koin.test.junit5.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@RunWith(RobolectricTestRunner::class)
class MainActivityTest : AutoCloseKoinTest() {

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
        if (GlobalContext.getOrNull() != null) {
            stopKoin()
        }
    }

    //убедимся, что Активити корректно создается
    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun testSearchEditText_InputIsWorked() {
        scenario.onActivity {
            val someText = "some text"
            val searchEditText = it.findViewById<TextInputEditText>(R.id.search_edit_text)
            searchEditText.setText(someText)

            assertNotNull(searchEditText.text)
            assertEquals(someText, searchEditText.text.toString())
        }
    }

    @Test
    fun testSearchEditText_GetData() {
        scenario.onActivity {
            val someText = "some search word"
            val searchEditText = it.findViewById<TextInputEditText>(R.id.search_edit_text)
            searchEditText.setText(someText)
            searchEditText.onEditorAction(EditorInfo.IME_ACTION_NEXT)

            assertEquals(someText, searchEditText.text.toString())
        }
    }

    @Test
    fun buttonFab_IsWorking() {
        scenario.onActivity {
            val button = it.findViewById<FloatingActionButton>(R.id.search_fab)
            button.performClick()

            assertNotNull(button)
        }
    }
}