package jt.projects.repository.room


import jt.projects.model.data.DataModel
import jt.projects.repository.datasource.DataSourceLocal
import jt.projects.repository.mapHistoryEntityToDataModel
import jt.projects.repository.toDataModel
import jt.projects.repository.toHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class RoomDatabaseImpl(private val historyDao: HistoryDao) :
    DataSourceLocal<Flow<DataModel>> {

    override suspend fun saveDataToDb(data: DataModel) = historyDao.insert(data.toHistoryEntity())

    override suspend fun getData(word: String): Flow<DataModel> {
        val data = historyDao.getDataByWord(word)?.toDataModel()
        if (data == null) throw Throwable("Перевод в локальном хранилище не найден")
        else return flowOf(data)
    }

    override suspend fun getAllDataFromDb(): Flow<DataModel> = mapHistoryEntityToDataModel(historyDao.all()).asFlow()

    override suspend fun clearDb() =  historyDao.deleteAll()

}

