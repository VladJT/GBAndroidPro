package jt.projects.gbandroidpro.model.room


import jt.projects.gbandroidpro.model.datasource.DataSourceLocal
import jt.projects.gbandroidpro.utils.mapHistoryEntityToSearchResult
import jt.projects.gbandroidpro.utils.toHistoryEntity
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

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
        return mapHistoryEntityToSearchResult(historyDao.all()).asFlow()
    }

    override suspend fun deleteAll() {
        historyDao.deleteAll()
    }

}

