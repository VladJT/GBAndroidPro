package jt.projects.gbandroidpro.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import jt.projects.gbandroidpro.R
import jt.projects.gbandroidpro.delay
import junit.framework.TestCase.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {
    companion object {
        private const val TIMEOUT = 5000L
    }

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setUp() {
        // сворачиваем все приложения
        uiDevice.pressHome()

        //Запускаем наше приложение
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)//Чистим бэкстек от запущенных ранее Активити
        context.startActivity(intent)

        //Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @After
    fun tearDown() = Unit

    //Убеждаемся, что приложение открыто (любой его элемент не null)
    @Test
    fun test_MainActivityIsStarted() {
        //Через uiDevice находим editText
        val searchEditText = uiDevice.findObject(By.res(packageName, "search_edit_text"))
        assertNotNull(searchEditText)
    }

    //ПРОВЕРКА, что поиск работает
    @Test
    fun test_SearchIsPositive() {
        val wordToSearch = "cool"
        val expectedWordCount = 6

        val searchEditText = uiDevice.findObject(By.res(packageName, "search_edit_text"))
        searchEditText.text = wordToSearch

        uiDevice.wait(
            Until.findObject(By.res(packageName, "main_activity_recyclerview")),
            TIMEOUT
        )

        val recView =
            UiCollection(UiSelector().className("androidx.recyclerview.widget.RecyclerView"))

        //Получаем количество элементов в контейнере
        val realCount = recView.getChildCount(
            UiSelector().className("android.widget.LinearLayout")
        )

        assertEquals(expectedWordCount, realCount)
    }

    // ПРОВЕРКА, что DescriptionActivity открывается
    @Test
    fun test_OpenDescriptionActivity() {
        val wordToSearch = "cool"
        val meaningToSearch = "холодно"

        val searchEditText = uiDevice.findObject(By.res(packageName, "search_edit_text"))
        searchEditText.text = wordToSearch
        onView(withId(R.id.search_edit_text)).perform(ViewActions.pressImeActionButton())

        delay(2000)

        //Находим нужный контейнер
        val recView =
            UiCollection(UiSelector().className("androidx.recyclerview.widget.RecyclerView"))

        //Получаем количество элементов в контейнере
        val count = recView.getChildCount(
            UiSelector().className("android.widget.LinearLayout")
        )

        //Находим элемент и запускаем его
        val first = recView.getChildByText(
            UiSelector().className("android.widget.LinearLayout"),
            meaningToSearch
        )

        first.click()

        val btnSound = uiDevice.findObject(By.res(packageName, "button_sound"))
        assertNotNull(btnSound)

        val descriptionTextView =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "description_textview")),
                TIMEOUT
            )
        assertEquals(meaningToSearch, descriptionTextView.text)
    }

    // ПРОВЕРКА, что по кнопке меню запускается HistoryActivity
    @Test
    fun test_OpenHistoryActivity() {
        val menuHistory = uiDevice.findObject(By.res(packageName, "menu_history"))
        menuHistory.click()
        // onView(ViewMatchers.withId(R.id.menu_history)).perform(ViewActions.click())

        val historyRecyclerView =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "history_activity_recyclerview")),
                TIMEOUT
            )
        assertNotNull(historyRecyclerView)

        val menuCleanHistory = uiDevice.findObject(By.res(packageName, "menu_clean_history"))
        assertNotNull(menuCleanHistory)
    }

}