package jt.projects.repository.room


import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.repository.datasource.DataSourceLocal
import jt.projects.repository.mapHistoryEntityToSearchResult
import jt.projects.repository.toHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEmpty

class RoomDatabaseImpl(private val historyDao: HistoryDao) : DataSourceLocal<Flow<DataModel>> {

    override suspend fun saveToDB(appState: AppState) {
        appState.toHistoryEntity()?.let {
            historyDao.insert(it)
        }
    }

    override suspend fun getData(word: String): Flow<DataModel> {
        //return listOf<DataModel>().asFlow()
        // Метод mapHistoryEntityToSearchResult описан во вспомогательном
        // классе SearchResultParser, в котором есть и другие методы для
        // трансформации данных
        return flowOf(historyDao.getDataByWord(word).toDataModel())
            .onEmpty {
                throw Throwable("Перевод в локальном хранилище не найден")
            }
    }

    override suspend fun getAllData(): Flow<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all()).asFlow()
    }

    override suspend fun deleteAll() {
        historyDao.deleteAll()
    }
}

