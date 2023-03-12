package jt.projects.gbandroidpro

import android.content.Intent
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.model.data.DataModel
import jt.projects.model.data.testData
import org.hamcrest.Matcher


val intentWithTestData =
    Intent(ApplicationProvider.getApplicationContext(), DescriptionActivity::class.java)
        .putExtra(DescriptionActivity.DATA_KEY, testData)


// word + count of result
val expectedData : Map<String, DataModel> = mapOf(
    "go" to DataModel(
        "go",
        "идти, ходить, вести, проходить, становиться, проходить, умирать, исчезать, заканчиваться, (исправно) работать, сочетаться, говорить, издавать звук, помещаться, попытка"
    ),
    "gg" to DataModel("gg", "Хорошая игра")
)

fun DataModel.getMeaningsCount(): Int {
    if (this.meanings.isNullOrBlank()) return 0
    return this.meanings.split(',').count()
}


const val TIMEOUT = 5000L


/**
вспомогательный метод - ставить на ожидание View, который мы планируем тестировать
 */
fun delay(value: Long): ViewAction? {
    return object : ViewAction {
        // будем возвращать root-view нашей кнопки
        override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()

        // getDescription() возвращает описание нашего Action
        override fun getDescription(): String = "wait for $value milliseconds"

        // “замораживаем” UI на value миллисекунд
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(value)
        }
    }
}