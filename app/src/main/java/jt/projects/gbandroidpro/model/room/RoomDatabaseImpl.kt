package jt.projects.gbandroidpro.model.room

import io.reactivex.Observable
import jt.projects.gbandroidpro.model.datasource.DataSource
import jt.projects.gbandroidpro.model.domain.DataModel

class RoomDatabaseImpl : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented")
    }
}

