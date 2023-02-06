package jt.projects.gbandroidpro.model.room


import jt.projects.gbandroidpro.model.datasource.DataSource
import jt.projects.gbandroidpro.model.domain.DataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class RoomDatabaseImpl : DataSource<Flow<DataModel>> {

    override suspend fun getData(word: String): Flow<DataModel> {
        return listOf<DataModel>().asFlow()
    }
}

