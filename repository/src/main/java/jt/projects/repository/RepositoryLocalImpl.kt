package jt.projects.repository

import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.model.data.SearchResultDTO
import jt.projects.repository.datasource.DataSourceLocal
import kotlinx.coroutines.flow.Flow

class RepositoryLocalImpl(private val dataSource: DataSourceLocal<Flow<DataModel>>) :
    RepositoryLocal<Flow<DataModel>> {

    override suspend fun saveData(data: DataModel) = dataSource.saveDataToDb(data)

    override suspend fun clearLocalRepo() = dataSource.clearDb()

    override suspend fun getTranslationByWord(word: String): Flow<DataModel> = dataSource.getData(word)

    override suspend fun getAllDataInLocalRepo(): Flow<DataModel> = dataSource.getAllDataFromDb()
}