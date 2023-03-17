package jt.projects.tests

import android.content.Intent
import android.view.View
import android.widget.EditText
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import jt.projects.model.data.testData
import org.hamcrest.Matcher


fun <T> intentWithTestData(clazz: Class<T>, key: String): Intent =
    Intent(ApplicationProvider.getApplicationContext(), clazz::class.java)
        .putExtra(key, testData)

/**
вспомогательный метод - ставить на ожидание View, который мы планируем тестировать
 */
private fun delay(value: Long): ViewAction? {
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

fun pauseByEspresso(value: Long) {
    Espresso.onView(ViewMatchers.isRoot()).perform(delay(value))
}

fun typeTextInChildViewWithId(id: Int, textToBeTyped: String): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Описание действия"
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById<View>(id) as EditText
            v.setText(textToBeTyped)
        }
    }
}

/**
 * метод, который будет принимать id требуемого view и возвращать нам готовый
ViewAction, который умеет нажимать на этот view
 */
fun tapOnItemWithId(id: Int) = object : ViewAction {
    override fun getConstraints(): Matcher<View>? {
        return null
    }

    override fun getDescription(): String {
        return "Нажимаем на view с указанным id"
    }

    override fun perform(uiController: UiController, view: View) {
        val v = view.findViewById(id) as View
        v.performClick()
    }
}