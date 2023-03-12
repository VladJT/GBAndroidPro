package jt.projects.gbandroidpro.UiAutomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith


/**
Используйте Espresso в качестве основной платформы для тестирования UI,
а UI Automator — для интеграционного тестирования (то есть запуска разных
экранов или переходов на экран или с экрана), а также для тестирования взаимодействия вашего
приложения с OS смартфона. То есть эти две библиотеки не противопоставляются друг другу, а
взаимодополняют.
Espresso тестирует все, что внутри приложения,
UI Automator — все, чтонаходится снаружи и с чем приложение может взаимодействовать.
Например, пуш-нотификации.
 */


//В аннотациях к классу мы явно указываем, что тесты используют JUnit и работают на версии
//операционной системы 18 и выше
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class InitialTest {
    // понадобится для запуска нужных экранов и получения packageName
    private val context = ApplicationProvider.getApplicationContext<Context>()

    //Путь к классам нашего приложения, которые мы будем тестировать
    private val packageName = context.packageName

    //Убеждаемся, что uiDevice не null
    @Test
    fun test_DeviceNotNull() {
        //Именно через UiDevice вы можете управлять устройством, открывать приложения
        //и находить нужные элементы на экране
        val uiDevice = UiDevice.getInstance(getInstrumentation())
        assertNotNull(uiDevice)
    }

    //Проверяем, что приложение существует
    @Test
    fun test_AppPackageNotNull() {
        assertNotNull(packageName)
    }

    //Проверяем, что Интент для запуска нашего приложения не null
    @Test
    fun test_MainActivityIntentNotNull() {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        assertNotNull(intent)
    }

}