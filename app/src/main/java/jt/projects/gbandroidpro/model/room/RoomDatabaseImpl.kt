package jt.projects.gbandroidpro.model.room


import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import jt.projects.gbandroidpro.model.datasource.DataSource
import jt.projects.gbandroidpro.model.domain.DataModel

class RoomDatabaseImpl : DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        return Observable.fromCallable { listOf<DataModel>() }
    }
}
