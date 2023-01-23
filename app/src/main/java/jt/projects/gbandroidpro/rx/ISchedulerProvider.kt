package jt.projects.gbandroidpro.rx

import io.reactivex.rxjava3.core.Scheduler


//In the sake of testing
interface ISchedulerProvider {

    fun ui(): Scheduler

    fun io(): Scheduler
}
