package jt.projects.gbandroidpro.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jt.projects.gbandroidpro.presentation.ui.main.MainActivity

/**
 * Модуль для Activity. Так как мы используем дополнительную библиотеку поддержки для Android, то все
становится гораздо проще при помощи ContributesAndroidInjector. Он позволяет внедрять
зависимости в Activity (нашу ViewModel) благодаря простому AndroidInjection.inject(this) в методе
onCreate
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}