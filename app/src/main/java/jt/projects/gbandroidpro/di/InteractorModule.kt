package jt.projects.gbandroidpro.di

import dagger.Module
import dagger.Provides
import jt.projects.gbandroidpro.interactor.MainInteractorImpl
import jt.projects.gbandroidpro.model.domain.DataModel
import jt.projects.gbandroidpro.model.repository.Repository
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE)
        repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL)
        repositoryLocal: Repository<List<DataModel>>
    ) = MainInteractorImpl(repositoryRemote, repositoryLocal)
}