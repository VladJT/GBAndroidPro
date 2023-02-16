package jt.projects.core.splash_screen

import android.app.Activity

interface ISplashScreen {
    var activity: Activity
    fun init(activity: Activity)
    fun show()
}