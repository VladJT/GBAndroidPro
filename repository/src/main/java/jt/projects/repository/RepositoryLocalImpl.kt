package jt.projects.repository

import jt.projects.model.data.DataModel
import jt.projects.repository.datasource.DataSourceLocal
import kotlinx.coroutines.flow.Flow

class RepositoryLocalImpl(private val dataSource: DataSourceLocal<Flow<DataModel>>) :
    RepositoryLocal<Flow<DataModel>> {

    override suspend fun saveData(data: DataModel) = dataSource.saveDataToDb(data)

    override suspend fun clearAllData() = dataSource.clearDb()

    override suspend fun getDataByWord(word: String): Flow<DataModel> =
        dataSource.getDataByWord(word)

    override suspend fun getAllData(): Flow<DataModel> = dataSource.getAllDataFromDb()
}