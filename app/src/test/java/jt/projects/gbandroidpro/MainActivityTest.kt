package jt.projects.gbandroidpro

import android.view.inputmethod.EditorInfo
import androidx.test.core.app.ActivityScenario
import com.google.android.material.textfield.TextInputEditText
import jt.projects.gbandroidpro.presentation.ui.main.MainActivity
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowToast
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
     //   stopKoin()
        scenario.close()
    }

    @Test
    fun testSearchEditText_Input() {
        scenario.onActivity {
            val searchEditText = it.findViewById<TextInputEditText>(R.id.search_edit_text)
            searchEditText.setText("some word")

            assertNotNull(searchEditText.text)
            assertEquals("some word", searchEditText.text.toString())
        }
    }

    fun testSearchEditText_GetData() {
        scenario.onActivity {
            val searchEditText = it.findViewById<TextInputEditText>(R.id.search_edit_text)
            searchEditText.setText("cool")
            searchEditText.onEditorAction(EditorInfo.IME_ACTION_NEXT)
            ShadowHandler.idleMainLooper()
            assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo(("Ваш текст"))
        }
    }


}