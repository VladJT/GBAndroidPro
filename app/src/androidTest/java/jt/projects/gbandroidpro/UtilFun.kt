package jt.projects.gbandroidpro

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

/**
вспомогательный метод - ставить на ожидание View, который мы планируем тестировать
 */
fun delay(value: Long): ViewAction? {
    return object : ViewAction {
        // будем возвращать root-view нашей кнопки
        override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()

        // getDescription() возвращает описание нашего Action
        override fun getDescription(): String = "wait for $value milliseconds"

        // “замораживаем” UI на 2 секунды
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(value)
        }
    }
}