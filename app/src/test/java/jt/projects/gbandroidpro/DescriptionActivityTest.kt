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
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.model.data.DataModel
import junit.framework.TestCase.*
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class DescriptionActivityTest {

    lateinit var scenario: ActivityScenario<DescriptionActivity>
    lateinit var activity: DescriptionActivity

    val testData = DataModel(
        "go",
        "бежать",
        "imageUrl",
        "https://vimbox-tts.skyeng.ru/api/v1/tts?text=beer+garden&lang=en&voice=male_2"
    )

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(DescriptionActivity::class.java)

        activity = Robolectric.buildActivity(DescriptionActivity::class.java, Intent().apply {
            putExtra(
                DescriptionActivity.WORD_EXTRA,
                testData
            )
        })
            .create()
            .visible()
            .start()
            .resume()
            .get()
    }

    @After
    fun tearDown() {
        stopKoin()
        scenario.close()
    }

    //проверим статический метод getIntent()
    @Test
    fun activityCreateIntent_NotNull() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val intent = DescriptionActivity.getIntent(context, testData)
        assertNotNull(intent)

        val bundle = intent.extras
        assertNotNull(bundle)
        assertEquals(testData, bundle?.getParcelable(DescriptionActivity.WORD_EXTRA))
    }



    //убедимся, что Активити корректно создается
    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }



    //убедимся, что Активити в нужном нам состоянии RESUMED
    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    // Проверим, что  элементы Активити  существуют, т.е. загружается нужный нам layout
    @Test
    fun activityTextView_NotNull() {
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

    // проверяем, что текстовые поля отображают ожидаемую информацию и видны на экране
    @Test
    fun descriptionHeader_HasText() {
        activity.also {
            val descriptionHeader =
                it.findViewById<TextView>(R.id.description_header)
            assertEquals(testData.text, descriptionHeader.text)
            assertEquals(View.VISIBLE, descriptionHeader.visibility)
        }
    }

    @Test
    fun description_HasText() {
        activity.also {
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