package jt.projects.repository

import jt.projects.model.data.AppState
import jt.projects.model.data.SearchResultDTO
import jt.projects.repository.datasource.DataSourceLocal
import kotlinx.coroutines.flow.Flow

class RepositoryLocalImpl(private val dataSource: DataSourceLocal<Flow<SearchResultDTO>>) :
    RepositoryLocal<Flow<SearchResultDTO>> {

    override suspend fun saveToDb(appState: AppState) = dataSource.saveToDB(appState)

    override suspend fun deleteAll() = dataSource.deleteAll()

    override suspend fun getData(word: String): Flow<SearchResultDTO> = dataSource.getData(word)

    override suspend fun getAllData(): Flow<SearchResultDTO> = dataSource.getAllData()
}