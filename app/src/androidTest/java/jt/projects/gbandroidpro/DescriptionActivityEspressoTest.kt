package jt.projects.gbandroidpro

import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionFragment
import jt.projects.model.data.testData
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

//    private val intent = intentWithTestData

    private lateinit var scenario: FragmentScenario<DescriptionFragment>

//    @Rule
//    @JvmField
//    val activityRule = activityScenarioRule<DescriptionActivity>(intent)

    /**
     * Обратите внимание, как создается Фрагмент. Есть два основных метода для создания:
    ● launchFragmentInContainer() нужен для запуска Фрагмента с UI;
    ● launchFragment — для Фрагментов без UI.
     */
    @Before
    fun setUp() {
        val fragmentArgs = bundleOf(DescriptionActivity.DATA_KEY to testData)
        scenario =
            launchFragmentInContainer(fragmentArgs, jt.projects.core.R.style.Theme_GBAndroidPro)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun fragment_AssertNotNull() {
        scenario.onFragment() {
            assertNotNull(it)
        }
    }

    @Test
    fun fragment_IsResumed() {
        scenario.onFragment {
            assertEquals(Lifecycle.State.RESUMED, it.viewLifecycleOwner.lifecycle.currentState)
        }
    }

    @Test
    fun activityTextView_NotNull() {
        scenario.onFragment {
            val descriptionHeader =
                it.requireActivity().findViewById<TextView>(R.id.description_header)
            assertNotNull(descriptionHeader)

            val transcription =
                it.requireActivity().findViewById<TextView>(R.id.transcription)
            assertNotNull(transcription)

            val description =
                it.requireActivity().findViewById<TextView>(R.id.description_textview)
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
        scenario.onFragment { assertEquals(false, it.isPressed) }
        onView(withId(R.id.button_sound)).perform(ViewActions.click())
        scenario.onFragment { assertEquals(true, it.isPressed) }
    }

}