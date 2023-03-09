package jt.projects.gbandroidpro

import android.content.Intent
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.model.data.testData
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
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

    private val intent =
        Intent(ApplicationProvider.getApplicationContext(), DescriptionActivity::class.java)
            .putExtra(DescriptionActivity.DATA_KEY, testData)

    private lateinit var scenario: ActivityScenario<DescriptionActivity>

    @get:Rule
    val activityRule = activityScenarioRule<DescriptionActivity>(intent)

    @Before
    fun setUp() {
        scenario = activityRule.scenario
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
        }
    }

    @Test
    fun activityHeader_HasText() {
        val assertion: ViewAssertion = matches(withText(testData.text))
        onView(withId(R.id.description_header)).check(assertion)
    }

    @Test
    fun activityDescriptionHasText() {
        val assertion: ViewAssertion = matches(withText(testData.meanings))
        onView(withId(R.id.description_textview)).check(assertion)
    }

    // isDisplayed() вернет true если хотя бы часть View отображается на экране
    @Test
    fun transcriptionTextView_IsDisplayed() {
        onView(withId(R.id.transcription)).check(matches(isDisplayed()))
    }

    // isCompletelyDisplayed() вернет true только если виджет полностью виден
    @Test
    fun descriptionImageView_IsCompletelyDisplayed() {
        onView(withId(R.id.description_imageview)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun buttonSound_IsVisible() {
        onView(withId(R.id.button_sound)).check(
            matches(
                withEffectiveVisibility(
                    Visibility
                        .VISIBLE
                )
            )
        )
    }

    @Test
    fun buttonSound_IsWorking() {
        scenario.onActivity { assertEquals(false, it.isPressed) }
        onView(withId(R.id.button_sound)).perform(ViewActions.click())
        scenario.onActivity { assertEquals(true, it.isPressed) }
    }

}