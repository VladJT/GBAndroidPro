package jt.projects.gbandroidpro.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import jt.projects.gbandroidpro.model.domain.AppState
import kotlinx.coroutines.*


//Создадим базовую ViewModel, куда вынесем общий для всех функционал
abstract class BaseViewModel<T : AppState> : ViewModel() {
    protected val _mutableLiveData: MutableLiveData<T> = MutableLiveData()

    public val liveDataForViewToObserve: LiveData<T>
        get() {
            return _mutableLiveData
        }

    protected val compositeDisposable = CompositeDisposable()
//    protected val schedulerProvider = SchedulerProvider()

    /**
     * Объявляем свой собственный скоуп
    В качестве аргумента передается CoroutineContext, который мы составляем
    через "+" из трех частей:
    - Dispatchers.Main говорит, что результат работы предназначен для
    основного потока;
    - SupervisorJob() позволяет всем дочерним корутинам выполняться
    независимо, то есть, если какая-то корутина упадёт с ошибкой, остальные
    будут выполнены нормально;
    - CoroutineExceptionHandler позволяет перехватывать и отрабатывать
    ошибки и краши
     */
    protected val viewModelCoroutineScope = CoroutineScope(// Scope - скоуп в котором будут существовать корутины
        Dispatchers.Main // механизм управления потоками
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })


    // обрабатываем ошибки в конкретной имплементации ViewModel
    abstract fun handleError(error: Throwable)

    abstract fun getData(word: String)

    // !! Единственный метод класса ViewModel, который вызывается перед  уничтожением Activity
    override fun onCleared() {
        Log.d("TAG", "onCleared ViewModel")
        compositeDisposable.clear()
        cancelJob()
        super.onCleared()
    }

    // Завершаем все незавершённые корутины, потому что пользователь закрыл экран
    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}