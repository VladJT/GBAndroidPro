package jt.projects.gbandroidpro

import android.app.Application
import jt.projects.gbandroidpro.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(application, repoModule, roomModule, mainScreen, historyScreen))
        }
    }
}
