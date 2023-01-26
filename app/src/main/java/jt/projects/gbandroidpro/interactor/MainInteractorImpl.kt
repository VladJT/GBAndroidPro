package jt.projects.gbandroidpro.interactor


import io.reactivex.rxjava3.core.Observable
import jt.projects.gbandroidpro.di.NAME_LOCAL
import jt.projects.gbandroidpro.di.NAME_REMOTE
import jt.projects.gbandroidpro.model.domain.AppState
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.repository.Repository
import javax.inject.Inject
import javax.inject.Named

//class MainInteractorImpl(
//    // Снабжаем интерактор репозиторием для получения локальных или внешних данных
//    private val remoteRepo: Repository<List<DataModel>>,
//    private val localRepo: Repository<List<DataModel>>
//)
//
class MainInteractorImpl @Inject constructor(
    @Named(NAME_REMOTE) val repositoryRemote: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) val repositoryLocal: Repository<List<DataModel>>
) : Interactor<AppState> {

    // Интерактор лишь запрашивает у репозитория данные, детали имплементации интерактору неизвестны
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            //оператор map преобразует элементы цепочки согласно переданному ему правилу. Чаще - по лямбде
            repositoryRemote.getData(word).map { AppState.Success(it) }
        } else {
            repositoryLocal.getData(word).map { AppState.Success(it) }
        }
    }
}