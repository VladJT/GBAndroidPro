package jt.projects.gbandroidpro.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import jt.projects.gbandroidpro.App
import jt.projects.gbandroidpro.others.Test
import jt.projects.gbandroidpro.presentation.ui.history.HistoryActivity
import jt.projects.gbandroidpro.presentation.ui.history.HistoryInteractorImpl
import jt.projects.gbandroidpro.presentation.ui.history.HistoryViewModel
import jt.projects.gbandroidpro.presentation.ui.main.MainActivity
import jt.projects.gbandroidpro.presentation.ui.main.MainInteractorImpl
import jt.projects.gbandroidpro.presentation.ui.main.MainViewModel
import jt.projects.repository.RepositoryImpl
import jt.projects.repository.RepositoryLocalImpl
import jt.projects.repository.retrofit.RetrofitImpl
import jt.projects.repository.room.HistoryDao
import jt.projects.repository.room.HistoryDatabase
import jt.projects.repository.room.RoomDatabaseImpl
import jt.projects.utils.NETWORK_SERVICE
import jt.projects.utils.SP_DB_NAME
import jt.projects.utils.network.INetworkStatus
import jt.projects.utils.network.NetworkStatus
import jt.projects.utils.shared_preferences.SimpleSharedPref
import jt.projects.utils.ui.CoilImageLoader
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


// зависимости, используемые во всём приложении
val application = module {
    // именованный scope
    scope(named("test_scope")) {
        scoped<Test> { (data: String) -> Test(data) }
    }

    single<App> { androidApplication().applicationContext as App }

    single<INetworkStatus>(qualifier = named(NETWORK_SERVICE)) { NetworkStatus(get()) }

    single { CoilImageLoader() }

    single {
        SimpleSharedPref(
            get<App>().getSharedPreferences(
                SP_DB_NAME,
                Context.MODE_PRIVATE
            )
        )
    }
}

val repoModule = module {
    //single<Repository<Flow<DataModel>>>(qualifier = named("repo")) { RepositoryImpl(RetrofitImpl()) } // for JUnit

    single { RepositoryImpl(RetrofitImpl()) }
    single { RepositoryLocalImpl(RoomDatabaseImpl(get<HistoryDao>())) }

}


val roomModule = module {
    val MIGRATION_2_4 = object : Migration(2, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //   database.execSQL("")
        }

    }
    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE HistoryEntity ADD COLUMN comment TEXT DEFAULT ''")
        }

    }

    single {
        Room.databaseBuilder(get(), HistoryDatabase::class.java, "History.db")
            .addMigrations(MIGRATION_2_4, MIGRATION_4_5)
            .build()
    }

    single { get<HistoryDatabase>().historyDao() }
}


//зависимости конкретного экрана
val mainScreen = module {
    scope<MainActivity> {
        scoped {
            MainInteractorImpl(
                get<RepositoryImpl>(),
                get<RepositoryLocalImpl>()
            )
        }

        viewModel { MainViewModel(get(), get(named(NETWORK_SERVICE))) }
    }

}

val historyScreen = module {
    scope<HistoryActivity> {
        scoped {
            HistoryInteractorImpl(
                get<RepositoryLocalImpl>()
            )
        }

        viewModel { HistoryViewModel(get(), get(named(NETWORK_SERVICE))) }

    }
}