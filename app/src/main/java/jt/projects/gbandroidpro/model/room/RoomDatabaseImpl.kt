package jt.projects.gbandroidpro.model.room


import jt.projects.gbandroidpro.model.datasource.DataSource
import jt.projects.gbandroidpro.model.domain.DataModel

class RoomDatabaseImpl : DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        return listOf<DataModel>()
    }
}

