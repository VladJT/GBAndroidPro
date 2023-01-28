package jt.projects.gbandroidpro.rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


//In the sake of testing
class SchedulerProvider : ISchedulerProvider {
    override fun ui(): io.reactivex.rxjava3.core.Scheduler = AndroidSchedulers.mainThread()
    override fun io(): io.reactivex.rxjava3.core.Scheduler = Schedulers.io()
}
