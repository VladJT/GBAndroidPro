package jt.projects.gbandroidpro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.rx.SchedulerProvider


//Создадим базовую ViewModel, куда вынесем общий для всех функционал
abstract class BaseViewModel<T : AppState> : ViewModel() {
    protected val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData()
    protected val compositeDisposable = CompositeDisposable()
    protected val schedulerProvider = SchedulerProvider()

    // Метод, благодаря которому Activity подписывается на изменение данных,
    // возвращает LiveData, через которую и передаются данные
    open fun getData(word: String): LiveData<T> = liveDataForViewToObserve

    // !! Единственный метод класса ViewModel, который вызывается перед  уничтожением Activity
    override fun onCleared() {
        Log.d("TAG", "onCleared ViewModel")
        compositeDisposable.clear()
        super.onCleared()
    }
}