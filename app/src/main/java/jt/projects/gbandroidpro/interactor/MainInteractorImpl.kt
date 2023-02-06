package jt.projects.gbandroidpro.interactor


import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.repository.Repository
import kotlinx.coroutines.flow.Flow


// Снабжаем интерактор репозиторием для получения локальных или внешних данных
class MainInteractorImpl(
    private val repositoryRemote: Repository<Flow<DataModel>>,
    private val repositoryLocal: Repository<Flow<DataModel>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): Flow<DataModel> {
        return if (fromRemoteSource) {
            repositoryRemote.getData(word)
        } else {
            repositoryLocal.getData(word)
        }
    }
}