package jt.projects.gbandroidpro.presentation.ui.main


import jt.projects.core.Interactor
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.repository.FakeDataSourceImpl
import jt.projects.repository.Repository
import jt.projects.repository.RepositoryLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList


// Снабжаем интерактор репозиторием для получения локальных или внешних данных
open class MainInteractorImpl(
    private val repositoryRemote: Repository<Flow<DataModel>>,
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val data = FakeDataSourceImpl().getDataByWord(word).toList()
        return AppState.Success(data)
    }

}

