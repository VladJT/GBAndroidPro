package jt.projects.gbandroidpro.model.repository

import jt.projects.gbandroidpro.model.datasource.DataSource
import jt.projects.gbandroidpro.model.domain.DataModel
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val dataSource: DataSource<Flow<DataModel>>) :
    Repository<Flow<DataModel>> {

    override suspend fun getData(word: String): Flow<DataModel> = dataSource.getData(word)
}