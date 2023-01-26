package jt.projects.gbandroidpro.di

import dagger.Module
import dagger.Provides
import jt.projects.gbandroidpro.model.datasource.DataSource
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.repository.Repository
import jt.projects.gbandroidpro.model.repository.RepositoryImpl
import jt.projects.gbandroidpro.model.retrofit.RetrofitImpl
import jt.projects.gbandroidpro.model.room.RoomDatabaseImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote:
                                         DataSource<List<DataModel>>
    ): Repository<List<DataModel>> =
        RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal:
                                        DataSource<List<DataModel>>): Repository<List<DataModel>> =
        RepositoryImpl(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> =
        RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> =
        RoomDatabaseImpl()
}
