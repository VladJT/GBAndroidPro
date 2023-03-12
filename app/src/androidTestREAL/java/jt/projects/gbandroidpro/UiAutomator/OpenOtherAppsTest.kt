package jt.projects.gbandroidpro.UiAutomator

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import junit.framework.TestCase.assertTrue
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenOtherAppsTest {

    private val uiDevice = UiDevice.getInstance(getInstrumentation())

    @Test
    fun test_OpenSettings() {
        uiDevice.pressHome()
        //Открываем экран со списком установленных приложений.

        //список приложений открывается свайпом снизу вверх на главном экране.
        //Метод swipe принимает координаты начальной и конечной точки свайпа.
        //В нашем случае это примерно снизу экрана строго вверх. Steps указывает, в
        //какое количество шагов мы хотим осуществить смахивание: чем выше число,
        //тем медленнее будет осуществляться свайп
        //     uiDevice.swipe(500, 1500, 500, 0, 5)
        uiDevice.swipe(
            uiDevice.displayWidth / 2,
            uiDevice.displayHeight - 50, uiDevice.displayWidth / 2, 0, 10
        )

        //Для других устройств список установленных приложений может открываться по другому.
        //Часто это иконка на главном экране под названием Apps.
        //Для этого достаточно свернуть все приложения через uiDevice.pressHome() и
        //и найти Apps на главном экране
        val allAppsButton = uiDevice.findObject(UiSelector().description("Settings"))
        allAppsButton.clickAndWaitForNewWindow()

        //Вполне возможно (встречается на старых устройствах), что приложения находятся
        // на вкладке Apps(будет еще вкладка Widgets).
        //Тогда еще найдем вкладку и выберем ее
        //val appsTab: UiObject = uiDevice.findObject(UiSelector().text("Apps"))
        //appsTab.click()

        val settings = uiDevice.findObject(UiSelector().packageName("com.android.settings"))
        assertTrue(settings.exists())

        //Приложений, обычно, установлено столько, что кнопка может быть за границей экрана
        //Тогда корневым контейнером будет Scrollable.
        //Если же все приложения умещаются на одном экране, то достаточно установить scrollable(false)
        val appViews = UiScrollable(UiSelector().scrollable(true))

        //Если прокрутка горизонтальная (встречается на старых устройствах), нужно установить
        // горизонтальную прокрутку (по умолчанию она вертикальная)
        //appViews.setAsHorizontalList()

        //Находим в контейнере настройки по названию иконки
        val settingsApp = appViews
            .getChildByText(
                UiSelector()
                    .className(TextView::class.java.name),
                "System"
            )

        //Открываем
        settingsApp.clickAndWaitForNewWindow()

        //Убеждаемся, что Настройки открыты
        val settingsValidation =
            uiDevice.findObject(UiSelector().textContains("Rules"))
        Assert.assertTrue(settingsValidation.exists())

    }

}