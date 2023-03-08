package jt.projects.gbandroidpro.presentation.ui.main


import jt.projects.core.Interactor
import jt.projects.model.data.AppState
import jt.projects.model.data.DataModel
import jt.projects.repository.Repository
import jt.projects.repository.RepositoryImpl
import jt.projects.repository.RepositoryLocal
import jt.projects.repository.fake.FakeDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList


// Снабжаем интерактор репозиторием для получения локальных или внешних данных
open class MainInteractorImpl(
    private val repositoryRemote: Repository<Flow<DataModel>>,
    private val repositoryLocal: RepositoryLocal<Flow<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val fakeRepo = RepositoryImpl(FakeDataSourceImpl())
        return AppState.Success(fakeRepo.getDataByWord(word).toList())
    }

}

