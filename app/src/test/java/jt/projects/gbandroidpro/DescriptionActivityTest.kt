package jt.projects.gbandroidpro


import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
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

    lateinit var activity: DescriptionActivity
    private lateinit var context: Context

    private val testData = DataModel(
        "go",
        "бежать",
        "imageUrl",
        "https://vimbox-tts.skyeng.ru/api/v1/tts?text=beer+garden&lang=en&voice=male_2"
    )

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()

        activity = Robolectric.buildActivity(DescriptionActivity::class.java, Intent().apply {
            putExtra(
                DescriptionActivity.DATA_KEY,
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
        assertNotNull(activity)
    }

    // проверка, что Activity в нужном нам состоянии RESUMED
    @Test
    fun activity_IsResumed() {
        assertEquals(Lifecycle.State.RESUMED, activity.lifecycle.currentState)
    }

    // Проверим, что  элементы Активити  существуют, т.е. загружается нужный нам layout
    @Test
    fun textViews_NotNull() {
        activity.also {
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
        activity.also {
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
        activity.also {
            val button = it.findViewById<Button>(R.id.button_sound)
            assertFalse(it.isPressed)

            button.performClick()

            assertNotNull(button)
            assertTrue(it.isPressed)
        }
    }

}