package jt.projects.repository.fake

import android.util.Log
import jt.projects.model.data.DataModel
import jt.projects.model.data.testData
import jt.projects.repository.datasource.DataSourceLocal
import jt.projects.utils.LOG_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataSourceLocalImpl : DataSourceLocal<Flow<DataModel>> {
    override suspend fun saveDataToDb(data: DataModel) {
        Log.d(LOG_TAG, "Fake DB saved")
    }

    override suspend fun getAllDataFromDb(): Flow<DataModel> {
        return flowOf(testData)
    }

    override suspend fun clearDb() {
        Log.d(LOG_TAG, "Fake DB cleared")
    }

    override suspend fun getDataByWord(word: String): Flow<DataModel> {
        return flowOf(testData)
    }
}