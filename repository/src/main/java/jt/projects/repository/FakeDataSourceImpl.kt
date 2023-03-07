package jt.projects.repository

import jt.projects.model.data.DataModel
import jt.projects.model.data.testData
import jt.projects.repository.datasource.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataSourceImpl : DataSource<Flow<DataModel>> {
    override suspend fun getDataByWord(word: String): Flow<DataModel> {
        return flowOf(testData)
    }
}