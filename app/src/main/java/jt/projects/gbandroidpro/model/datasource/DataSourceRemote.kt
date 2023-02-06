package jt.projects.gbandroidpro.model.datasource


import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.retrofit.RetrofitImpl
import kotlinx.coroutines.flow.Flow

// Для получения внешних данных мы будем использовать Retrofit
class DataSourceRemote(private val localProvider: RetrofitImpl = RetrofitImpl()) :
    DataSource<Flow<DataModel>> {

    override suspend fun getData(word: String): Flow<DataModel> = localProvider.getData(word)
}