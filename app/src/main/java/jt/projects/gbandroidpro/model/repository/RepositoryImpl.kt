package jt.projects.gbandroidpro.model.repository

import jt.projects.gbandroidpro.model.datasource.DataSource
import jt.projects.gbandroidpro.model.domain.DataModel

class RepositoryImpl(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    // Репозиторий возвращает данные, используя dataSource (локальный или внешний)
    //override fun getData(word: String): Observable<List<DataModel>> = dataSource.getData(word)

    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)
}