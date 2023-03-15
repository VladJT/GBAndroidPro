package jt.projects.gbandroidpro.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import jt.projects.gbandroidpro.R
import jt.projects.tests.TIMEOUT
import junit.framework.TestCase.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class HistoryActivityTest {

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

        // запускаем HistoryActivity
        val menuHistory = uiDevice.findObject(By.res(packageName, "menu_history"))
        menuHistory.click()

        uiDevice.wait(
            Until.findObject(By.res(packageName, "history_activity_recyclerview")),
            TIMEOUT
        )
    }

    @After
    fun tearDown() = Unit


    // проверим, что HistoryActivity запускается
    @Test
    fun test_HistoryActivityIsStarted() {
        val historyRecyclerView =
            uiDevice.findObject(By.res(packageName, "history_activity_recyclerview"))

        // если получен historyRecyclerView - значит HistoryActivity запущена
        assertNotNull(historyRecyclerView)
    }

    // проверим вызов диалога очистки истории
    @Test
    fun menuCleanHistory_IsWorked() {
        val menuCleanHistory = uiDevice.findObject(By.res(packageName, "menu_clean_history"))
        assertNotNull(menuCleanHistory)
        menuCleanHistory.click()

        val dialogText = context.getString(R.string.dialog_clean_history_message)
        uiDevice.wait(Until.findObject(By.text(dialogText)), TIMEOUT)
        val dialog = uiDevice.findObject(By.text(dialogText))
        assertNotNull(dialog)
    }

    // ПРОВЕРКА, что открывается DescriptionActivity
    @Test
    fun test_OpenDescriptionActivity() {
        //Находим нужный контейнер
        val recView =
            UiCollection(UiSelector().className("androidx.recyclerview.widget.RecyclerView"))

        //Получаем количество элементов в контейнере
        val count = recView.getChildCount(
            UiSelector().className("android.widget.LinearLayout")
        )

        if (count > 0) {
            //Находим 1-ый элемент и запускаем его
            val recViewItem = recView.getChild(UiSelector().clickable(true))
            recViewItem.clickAndWaitForNewWindow()

            val descriptionTextView =
                uiDevice.findObject(By.res(packageName, "description_textview"))
            assertNotNull(descriptionTextView)

            val btnSound = uiDevice.findObject(By.res(packageName, "button_sound"))
            assertNotNull(btnSound)
        }
    }
}