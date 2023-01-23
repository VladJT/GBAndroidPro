package jt.projects.gbandroidpro.model.datasource


import io.reactivex.rxjava3.core.Observable
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.room.RoomDatabaseImpl

// Для локальных данных используется Room
class DataSourceLocal(private val remoteProvider: RoomDatabaseImpl = RoomDatabaseImpl()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}