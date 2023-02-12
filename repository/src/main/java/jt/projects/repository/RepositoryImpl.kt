package jt.projects.repository

import jt.projects.repository.datasource.DataSource
import jt.projects.model.data.DataModel
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val dataSource: DataSource<Flow<DataModel>>) :
    Repository<Flow<DataModel>> {

    override suspend fun getData(word: String): Flow<DataModel> = dataSource.getData(word)
}