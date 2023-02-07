package jt.projects.gbandroidpro.model.datasource


import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.room.RoomDatabaseImpl
import kotlinx.coroutines.flow.Flow

// Для локальных данных используется Room
class DataSourceLocal(private val localProvider: RoomDatabaseImpl) :
    DataSource<Flow<DataModel>> {

    override suspend fun getData(word: String): Flow<DataModel> = localProvider.getData(word)
}