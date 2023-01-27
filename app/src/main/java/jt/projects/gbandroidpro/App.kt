package jt.projects.gbandroidpro

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import jt.projects.gbandroidpro.di.AppComponent
import jt.projects.gbandroidpro.di.DaggerAppComponent
import javax.inject.Inject

/** Обратите внимание на dispatchingAndroidInjector и интерфейс Dagger'а
HasAndroidInjector: мы переопределяем его метод androidInjector. Они
нужны для внедрения зависимостей в Activity. По своей сути — это вспомогательные
методы для разработчиков под Андроид для эффективного внедрения компонентов
платформы, таких как Активити, Сервис и т. п.
 */
class App : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    lateinit var appComponent: AppComponent

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        // appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}
