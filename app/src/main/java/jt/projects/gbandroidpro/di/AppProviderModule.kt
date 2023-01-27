package jt.projects.gbandroidpro.di

import dagger.Module
import dagger.Provides
import jt.projects.gbandroidpro.App
import javax.inject.Singleton


@Module
class AppProviderModule(app: App) {
    private val application: App

    init {
        this.application = app
    }

    @Provides
    @Singleton
    fun application(): App {
        return application
    }
}