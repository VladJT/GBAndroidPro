package jt.projects.gbandroidpro.di

import jt.projects.gbandroidpro.App
import jt.projects.gbandroidpro.interactor.MainInteractorImpl
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.repository.Repository
import jt.projects.gbandroidpro.model.repository.RepositoryImpl
import jt.projects.gbandroidpro.model.retrofit.RetrofitImpl
import jt.projects.gbandroidpro.model.room.RoomDatabaseImpl
import jt.projects.gbandroidpro.presentation.viewmodel.MainViewModel
import jt.projects.gbandroidpro.utils.Test
import jt.projects.gbandroidpro.utils.network.INetworkStatus
import jt.projects.gbandroidpro.utils.network.NetworkStatus
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
● module { } — создание модуля или субмодуля (модуля внутри модуля). Module очень похож на
модули в Dagger: это контейнер для коллекции зависимостей;
● factory { } — генерация зависимости каждый раз заново;
● single { } — генерация синглтона;
● get() — создание экземпляра класса.
 */

//  fun networkStatus(app: App): INetworkStatus = NetworkStatus(app)

// зависимости, используемые во всём приложении
val application = module {

    factory<Test> { (data: String) -> Test(data) }

    single<App> { androidApplication().applicationContext as App }

    single<Repository<Flow<DataModel>>>(qualifier = named(NAME_REMOTE)) {
        RepositoryImpl(RetrofitImpl())
    }

    single<Repository<Flow<DataModel>>>(qualifier = named(NAME_LOCAL)) {
        RepositoryImpl(RoomDatabaseImpl())
    }

    single<INetworkStatus>(qualifier = named(NETWORK_SERVICE)) { NetworkStatus() }
}

//зависимости конкретного экрана
val mainScreen = module {
    factory(qualifier = named(INTERACTOR)) {
        MainInteractorImpl(
            get(named(NAME_REMOTE)),
            get(named(NAME_LOCAL))
        )
    }

    //Koin из коробки поддерживает архитектурный компонент ViewModel через функцию viewModel { } для
    //определения зависимости
    viewModel {
        MainViewModel(
            get(named(INTERACTOR)),
            get(named(NETWORK_SERVICE))
        )
    }
}