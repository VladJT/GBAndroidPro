package jt.projects.gbandroidpro.model.datasource


import jt.projects.gbandroidpro.model.domain.AppState

// Наследуемся от DataSource и добавляем нужный метод
interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
    suspend fun deleteAll()
}