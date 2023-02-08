package jt.projects.gbandroidpro.di

import androidx.room.Room
import jt.projects.gbandroidpro.App
import jt.projects.gbandroidpro.interactor.HistoryInteractorImpl
import jt.projects.gbandroidpro.interactor.MainInteractorImpl
import jt.projects.gbandroidpro.model.repository.RepositoryImpl
import jt.projects.gbandroidpro.model.repository.RepositoryLocalImpl
import jt.projects.gbandroidpro.model.retrofit.RetrofitImpl
import jt.projects.gbandroidpro.model.room.HistoryDao
import jt.projects.gbandroidpro.model.room.HistoryDatabase
import jt.projects.gbandroidpro.model.room.RoomDatabaseImpl
import jt.projects.gbandroidpro.presentation.viewmodel.HistoryViewModel
import jt.projects.gbandroidpro.presentation.viewmodel.MainViewModel
import jt.projects.gbandroidpro.utils.Test
import jt.projects.gbandroidpro.utils.ui.CoilImageLoader
import jt.projects.gbandroidpro.utils.network.INetworkStatus
import jt.projects.gbandroidpro.utils.network.NetworkStatus
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

    single<RepositoryImpl> { RepositoryImpl(RetrofitImpl()) }

    single<RepositoryLocalImpl> { RepositoryLocalImpl(RoomDatabaseImpl(get<HistoryDao>())) }

    single<INetworkStatus>(qualifier = named(NETWORK_SERVICE)) { NetworkStatus() }

    single<CoilImageLoader> { CoilImageLoader() }

    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, "History.db").build() }

    single { get<HistoryDatabase>().historyDao() }
}

val roomModule = module {
//    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, "History.db").build() }
//
//    single { get<HistoryDatabase>().historyDao() }
}


//зависимости конкретного экрана
val mainScreen = module {
    factory {
        MainInteractorImpl(
            get<RepositoryImpl>(),
            get<RepositoryLocalImpl>()
        )
    }

    //Koin из коробки поддерживает архитектурный компонент ViewModel через функцию viewModel { } для
    //определения зависимости
    viewModel {
        MainViewModel(
            get<MainInteractorImpl>(),
            get(named(NETWORK_SERVICE))
        )
    }
}

val historyScreen = module {
    factory {
        HistoryInteractorImpl(
            get<RepositoryImpl>(),
            get<RepositoryLocalImpl>()
        )
    }

    viewModel {
        HistoryViewModel(
            get<HistoryInteractorImpl>(),
            get(named(NETWORK_SERVICE))
        )
    }
}