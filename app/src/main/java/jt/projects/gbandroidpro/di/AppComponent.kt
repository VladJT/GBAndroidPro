package jt.projects.gbandroidpro.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import jt.projects.gbandroidpro.App
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
        NetworkModule::class,
        AppProviderModule::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent {
    // Этот билдер мы вызовем из класса App
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun appModule(appProviderModule: AppProviderModule): Builder
        fun build(): AppComponent
    }

    // Наш кастомный Application
    fun inject(app: App)
}
