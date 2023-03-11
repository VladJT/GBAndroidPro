package jt.projects.gbandroidpro


import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.activityScenarioRule
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.model.data.testData
import junit.framework.TestCase.*
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class DescriptionActivityTest {

    private lateinit var context: Context

    private lateinit var scenario: ActivityScenario<DescriptionActivity>

    private val intent =
        Intent(ApplicationProvider.getApplicationContext(), DescriptionActivity::class.java)
            .putExtra(DescriptionActivity.DATA_KEY, testData)

    @get:Rule
    val activityRule = activityScenarioRule<DescriptionActivity>(intent)


    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        scenario = activityRule.scenario

    }

    @After
    fun tearDown() {
        stopKoin()
        scenario.close()
    }

    //проверим статический метод getIntent()
    @Test
    fun activityCreateIntent_NotNull() {
        val intent = DescriptionActivity.getIntent(context, testData)
        assertNotNull(intent)

        val bundle = intent.extras
        assertNotNull(bundle)
        assertEquals(testData, bundle?.getParcelable(DescriptionActivity.DATA_KEY))
    }


    // проверка, что Activity корректно создается
    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    // проверка, что Activity в нужном нам состоянии RESUMED
    @Test
    fun activity_IsResumed() {
        scenario.onActivity {
            assertEquals(Lifecycle.State.RESUMED, it.lifecycle.currentState)
        }
    }

    // Проверим, что  элементы Активити  существуют, т.е. загружается нужный нам layout
    @Test
    fun textViews_NotNull() {
        scenario.onActivity {
            val descriptionHeader =
                it.findViewById<TextView>(R.id.description_header)
            assertNotNull(descriptionHeader)

            val transcription =
                it.findViewById<TextView>(R.id.transcription)
            assertNotNull(transcription)

            val description =
                it.findViewById<TextView>(R.id.description_textview)
            assertNotNull(description)

            val imageView = it.findViewById<ImageView>(R.id.description_imageview)
            assertNotNull(imageView)
        }
    }

    @Test
    fun imageView_NotNull() {
        scenario.onActivity {
            val imageView = it.findViewById<ImageView>(R.id.description_imageview)
            assertNotNull(imageView)
        }
    }

    // проверяем, что текстовые поля отображают ожидаемую информацию и видны на экране
    @Test
    fun descriptionHeader_HasText() {
        scenario.onActivity {
            val descriptionHeader =
                it.findViewById<TextView>(R.id.description_header)
            assertEquals(testData.text, descriptionHeader.text)
            assertEquals(View.VISIBLE, descriptionHeader.visibility)
        }
    }

    @Test
    fun description_HasText() {
        scenario.onActivity {
            val description =
                it.findViewById<TextView>(R.id.description_textview)
            assertEquals(testData.meanings, description.text)
            assertEquals(View.VISIBLE, description.visibility)
        }
    }

    @Test
    fun buttonSound_IsWorking() {
        scenario.onActivity {
            val button = it.findViewById<Button>(R.id.button_sound)
            assertFalse(it.isPressed)

            button.performClick()

            assertNotNull(button)
            assertTrue(it.isPressed)
        }
    }

}