package jt.projects.gbandroidpro.presentation.ui.base

import androidx.appcompat.app.AppCompatActivity
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.presentation.viewmodel.BaseViewModel


abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    // В каждой Активити будет своя ViewModel, которая наследуется от BaseViewModel
    abstract val model: BaseViewModel<T>

    // Каждая Активити будет отображать какие-то данные в соответствующем состоянии
    abstract fun renderData(state: T)
}