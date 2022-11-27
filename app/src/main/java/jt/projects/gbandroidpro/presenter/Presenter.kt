package jt.projects.gbandroidpro.presenter

import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.ui.base.BaseView


// На уровень выше находится презентер, который уже ничего не знает ни о контексте, ни о фреймворке
interface Presenter<T : AppState, V : BaseView> {
    fun attachView(view: V)
    fun detachView(view: V)

    // Получение данных с флагом isOnline(из Интернета или нет)
    fun getData(word: String, isOnline: Boolean)
}