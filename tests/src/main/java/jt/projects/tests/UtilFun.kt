package jt.projects.tests

import android.content.Intent
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import jt.projects.model.data.testData



fun<T> intentWithTestData(clazz: Class<T>, key: String): Intent =
    Intent(ApplicationProvider.getApplicationContext(), clazz::class.java)
        .putExtra(key, testData)

/**
вспомогательный метод - ставить на ожидание View, который мы планируем тестировать
 */
fun delay(value: Long): ViewAction? {
    return object : ViewAction {
        // будем возвращать root-view нашей кнопки
        override fun getConstraints(): org.hamcrest.Matcher<View> = ViewMatchers.isRoot()

        // getDescription() возвращает описание нашего Action
        override fun getDescription(): String = "wait for $value milliseconds"

        // “замораживаем” UI на value миллисекунд
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(value)
        }
    }
}