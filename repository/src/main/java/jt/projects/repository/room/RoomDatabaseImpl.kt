package jt.projects.repository.room


import jt.projects.model.data.AppState
import jt.projects.model.data.SearchResultDTO
import jt.projects.repository.datasource.DataSourceLocal
import jt.projects.repository.mapHistoryEntityToSearchResultDTO
import jt.projects.repository.toHistoryEntity
import jt.projects.repository.toSearchResultDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class RoomDatabaseImpl(private val historyDao: HistoryDao) :
    DataSourceLocal<Flow<SearchResultDTO>> {

    override suspend fun saveToDB(appState: AppState) {
        appState.toHistoryEntity()?.let {
            historyDao.insert(it)
        }
    }

    override suspend fun getData(word: String): Flow<SearchResultDTO> {
        //return listOf<DataModel>().asFlow()
        // Метод mapHistoryEntityToSearchResult описан во вспомогательном
        // классе SearchResultParser, в котором есть и другие методы для
        // трансформации данных
        val rez = historyDao.getDataByWord(word)?.toSearchResultDTO()
        if (rez == null) throw Throwable("Перевод в локальном хранилище не найден")
        else return flowOf(rez)
    }

    override suspend fun getAllData(): Flow<SearchResultDTO> {
        return mapHistoryEntityToSearchResultDTO(historyDao.all()).asFlow()
    }

    override suspend fun deleteAll() {
        historyDao.deleteAll()
    }
}

