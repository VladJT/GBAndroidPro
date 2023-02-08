package jt.projects.gbandroidpro.model.repository

import jt.projects.gbandroidpro.model.datasource.DataSourceLocal
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import kotlinx.coroutines.flow.Flow

class RepositoryLocalImpl(private val dataSource: DataSourceLocal<Flow<DataModel>>) :
    RepositoryLocal<Flow<DataModel>> {

    override suspend fun saveToDb(appState: AppState) = dataSource.saveToDB(appState)

    override suspend fun getData(word: String): Flow<DataModel> = dataSource.getData(word)
}

//// Для локальных данных используется Room
//class DataSourceLocal(private val localProvider: RoomDatabaseImpl) :
//    DataSource<Flow<DataModel>> {
//
//    override suspend fun getData(word: String): Flow<DataModel> = localProvider.getData(word)
//}