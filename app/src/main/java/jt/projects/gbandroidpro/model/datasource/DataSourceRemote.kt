package jt.projects.gbandroidpro.model.datasource


import io.reactivex.rxjava3.core.Observable
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.retrofit.RetrofitImpl

// Для получения внешних данных мы будем использовать Retrofit
class DataSourceRemote(private val remoteProvider: RetrofitImpl = RetrofitImpl()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}