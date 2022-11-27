package jt.projects.gbandroidpro.ui.base

import jt.projects.gbandroidpro.model.domain.AppState

// Нижний уровень. View знает о контексте и фреймворке
interface BaseView {
    // View имеет только один метод, в который приходит некое состояние приложения
    fun renderData(appState: AppState)
}