package jt.projects.gbandroidpro.di

import dagger.Module
import dagger.Provides
import jt.projects.gbandroidpro.App
import jt.projects.gbandroidpro.utils.network.INetworkStatus
import jt.projects.gbandroidpro.utils.network.NetworkStatus
import javax.inject.Singleton

@Module(
    includes = [AppProviderModule::class]
)
class NetworkModule {

    @Singleton
    @Provides
    fun networkStatus(app: App): INetworkStatus = NetworkStatus(app)
}