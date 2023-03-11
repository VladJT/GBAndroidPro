package jt.projects.gbandroidpro.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import jt.projects.model.data.EMPTY_RESPONSE_EXCEPTION
import junit.framework.TestCase.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class MainActivityTest {
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

        //Чистим бэкстек от запущенных ранее Активити
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        //Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @After
    fun tearDown() = Unit

    //Убеждаемся, что приложение открыто (любой его элемент не null)
    @Test
    fun test_MainActivityIsStarted() {
        val searchEditText = uiDevice.findObject(By.res(packageName, "search_edit_text"))
        assertNotNull(searchEditText)
    }

    //ПРОВЕРКА, что поиск работает на корректном запросе
    @Test
    fun positiveSearch_IsWorked() {
        val wordToSearch = "gg"
        val expectedMeanings = "Хорошая игра"
        val expectedWordsCount = 1

        val searchEditText = uiDevice.findObject(By.res(packageName, "search_edit_text"))
        searchEditText.text = wordToSearch

        // PAUSE для получения результатов
        uiDevice.wait(Until.findObject(By.text(expectedMeanings)), TIMEOUT)

        val recView =
            UiCollection(UiSelector().className("androidx.recyclerview.widget.RecyclerView"))

        //Получаем количество элементов в контейнере
        val realCount = recView.getChildCount(
            UiSelector().className("android.widget.LinearLayout")
        )

        assertEquals(expectedWordsCount, realCount)
    }

    @Test
    fun negativeSearch_IsWorked() {
        val wordToSearch = "gggg"
        val searchEditText = uiDevice.findObject(By.res(packageName, "search_edit_text"))
        searchEditText.text = wordToSearch

        // ждем errorLayout
        val errorFrameLayout =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "error_linear_layout")),
                TIMEOUT
            )
        assertNotNull(errorFrameLayout)

        // виден текст ошибки + reloadButton
        val reloadButton = uiDevice.wait(
            Until.findObject(By.res(packageName, "reload_button")),
            TIMEOUT
        )
        assertNotNull(reloadButton)

        val errorText = uiDevice.findObject(By.res(packageName, "error_textview"))
        assertEquals(errorText.text, EMPTY_RESPONSE_EXCEPTION.message)
    }

    // ПРОВЕРКА, что открывается DescriptionActivity
    @Test
    fun test_OpenDescriptionActivity() {
        val wordToSearch = "cool"
        val expectedMeanings = "холодно"

        val searchEditText = uiDevice.findObject(By.res(packageName, "search_edit_text"))
        searchEditText.text = wordToSearch

        // PAUSE для получения результатов
        uiDevice.wait(Until.findObject(By.text(expectedMeanings)), TIMEOUT)

        //Находим нужный контейнер
        val recView =
            UiCollection(UiSelector().className("androidx.recyclerview.widget.RecyclerView"))

        //Находим элемент и запускаем его
        val recViewItem = recView.getChildByText(
            UiSelector().className("android.widget.LinearLayout"),
            expectedMeanings
        )
        recViewItem.clickAndWaitForNewWindow()

        // если появилась кнопка прослушать, значит DescriptionActivity запущена
        val btnSound = uiDevice.findObject(UiSelector().textContains("Прослушать"))
        assertTrue(btnSound.exists())
    }
}