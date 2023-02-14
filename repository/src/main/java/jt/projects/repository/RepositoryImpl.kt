package jt.projects.repository

import jt.projects.repository.datasource.DataSource
import jt.projects.model.data.SearchResultDTO
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val dataSource: DataSource<Flow<SearchResultDTO>>) :
    Repository<Flow<SearchResultDTO>> {

    override suspend fun getData(word: String): Flow<SearchResultDTO> = dataSource.getData(word)
}