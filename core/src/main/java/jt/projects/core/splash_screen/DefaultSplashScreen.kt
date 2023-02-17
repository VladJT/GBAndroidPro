package jt.projects.core.splash_screen

import android.app.Activity
import android.os.CountDownTimer
import android.view.View
import android.view.ViewTreeObserver

const val CD_TIME = 2000L
const val CD_INTERVAL = 1000L

class DefaultSplashScreen() : ISplashScreen {
    override lateinit var activity: Activity

    override fun init(a: Activity) {
        this.activity = a
    }

    override fun show() {
        var isHideSplashScreen = false
        object : CountDownTimer(CD_TIME, CD_INTERVAL) {
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