package jt.projects.gbandroidpro.di

import jt.projects.repository.RepositoryImpl
import jt.projects.repository.RepositoryLocalImpl
import jt.projects.repository.retrofit.RetrofitImpl
import jt.projects.repository.room.HistoryDao
import jt.projects.repository.room.RoomDatabaseImpl
import org.koin.dsl.module

val repoModule = module {
    //single<Repository<Flow<DataModel>>>(qualifier = named("repo")) { RepositoryImpl(RetrofitImpl()) } // for JUnit
    single { RepositoryImpl(RetrofitImpl()) }
    single { RepositoryLocalImpl(RoomDatabaseImpl(get<HistoryDao>())) }
}