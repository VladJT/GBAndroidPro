package jt.projects.gbandroidpro.di

import jt.projects.repository.RepositoryImpl
import jt.projects.repository.RepositoryLocalImpl
import jt.projects.repository.fake.FakeDataSourceImpl
import jt.projects.repository.fake.FakeDataSourceLocalImpl
import org.koin.dsl.module

val repoModule = module {
    single { RepositoryImpl(FakeDataSourceImpl()) }
    single { RepositoryLocalImpl(FakeDataSourceLocalImpl()) }
}