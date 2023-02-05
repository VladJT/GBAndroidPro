package jt.projects.gbandroidpro.interactor


import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow


//    Снабжаем интерактор репозиторием для получения локальных или внешних данных
class MainInteractorImpl(
    private val repositoryRemote: Repository<Flow<DataModel>>,
    private val repositoryLocal: Repository<Flow<DataModel>>
) : Interactor<DataModel> {

    // Добавляем suspend
    override suspend fun getData(word: String, fromRemoteSource: Boolean): List<DataModel> {
        return if (fromRemoteSource) {
           repositoryRemote.getData(word).toList()
        } else {
            repositoryLocal.getData(word).toList()
        }
    }


    // Интерактор лишь запрашивает у репозитория данные, детали имплементации интерактору неизвестны
//    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
//        return if (fromRemoteSource) {
//            //оператор map преобразует элементы цепочки согласно переданному ему правилу. Чаще - по лямбде
//            repositoryRemote.getData(word).map { AppState.Success(it) }
//        } else {
//            repositoryLocal.getData(word).map { AppState.Success(it) }
//        }
//    }
}