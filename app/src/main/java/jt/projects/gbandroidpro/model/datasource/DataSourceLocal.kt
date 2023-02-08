package jt.projects.gbandroidpro.model.datasource


import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.room.RoomDatabaseImpl
import kotlinx.coroutines.flow.Flow

// Наследуемся от DataSource и добавляем нужный метод
interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}