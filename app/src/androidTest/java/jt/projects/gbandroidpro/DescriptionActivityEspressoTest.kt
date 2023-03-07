package jt.projects.gbandroidpro

import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import junit.framework.TestCase
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
class DescriptionActivityEspressoTest {
    private lateinit var scenario: ActivityScenario<DescriptionActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(DescriptionActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityTextView_NotNull() {
        scenario.onActivity {
            val descriptionHeader =
                it.findViewById<TextView>(R.id.description_header)
            TestCase.assertNotNull(descriptionHeader)

            val transcription =
                it.findViewById<TextView>(R.id.transcription)
            TestCase.assertNotNull(transcription)

            val description =
                it.findViewById<TextView>(R.id.description_textview)
            TestCase.assertNotNull(description)
        }
    }

}