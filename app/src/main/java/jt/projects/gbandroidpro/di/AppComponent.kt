package jt.projects.gbandroidpro.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import jt.projects.gbandroidpro.App
import jt.projects.gbandroidpro.ui.main.MainActivity
import javax.inject.Singleton

// Тут мы прописываем все наши модули, включая AndroidSupportInjectionModule.
// Этот класс создаётся Dagger’ом. Он как раз связан с аннотацией
// ContributesAndroidInjector выше и позволяет внедрять в Activity все
// необходимые зависимости
@Singleton
@Component(
    modules = [

        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent {
    // Этот билдер мы вызовем из класса App
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    // Наш кастомный Application
    fun inject(app: App)
}
