package jt.projects.gbandroidpro.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import jt.projects.gbandroidpro.ui.main.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}