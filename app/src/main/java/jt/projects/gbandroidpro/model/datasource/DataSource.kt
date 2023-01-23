package jt.projects.gbandroidpro.model.datasource

import io.reactivex.rxjava3.core.Observable


// Источник данных для репозитория (Интернет, БД и т. п.)
interface DataSource<T> {
    fun getData(word: String): Observable<T>
}