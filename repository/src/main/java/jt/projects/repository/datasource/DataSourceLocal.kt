package jt.projects.repository.datasource

import jt.projects.model.data.AppState


// Наследуемся от DataSource и добавляем нужный метод
interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
    suspend fun deleteAll()
}