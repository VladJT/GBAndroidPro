package jt.projects.gbandroidpro

import android.app.Application
import jt.projects.gbandroidpro.di.application
import jt.projects.gbandroidpro.di.historyScreen
import jt.projects.gbandroidpro.di.mainScreen
import jt.projects.gbandroidpro.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(application, roomModule, mainScreen, historyScreen))
        }
    }
}
