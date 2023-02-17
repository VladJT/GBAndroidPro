package jt.projects.repository

import jt.projects.model.data.DataModel
import jt.projects.repository.datasource.DataSource
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val dataSource: DataSource<Flow<DataModel>>) :
    Repository<Flow<DataModel>> {

    override suspend fun getDataByWord(word: String): Flow<DataModel> =
        dataSource.getDataByWord(word)
}