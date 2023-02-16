package jt.projects.core.splash_screen

import android.app.Activity
import android.os.CountDownTimer
import android.view.View
import android.view.ViewTreeObserver

class DefaultSplashScreen() : ISplashScreen {
    override lateinit var activity: Activity

    override fun init(a: Activity) {
        this.activity = a
    }

    override fun show() {
        setSplashScreenDuration()
    }

    fun setSplashScreenDuration() {
        var isHideSplashScreen = false
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()
        val content: View = activity.findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }
}