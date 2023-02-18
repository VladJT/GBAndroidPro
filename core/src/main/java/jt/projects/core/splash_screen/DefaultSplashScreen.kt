package jt.projects.core.splash_screen

import android.app.Activity
import android.os.CountDownTimer
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import java.util.concurrent.Executors

const val SPLASH_SCREEN_INTERVAL = 2000L

class DefaultSplashScreen() : ISplashScreen {
    override lateinit var activity: Activity

    override fun init(a: Activity) {
        this.activity = a
    }

    override fun show() {
        val splashScreen = activity.installSplashScreen()

        splashScreen.setKeepOnScreenCondition() {
            true
        }
        Executors.newSingleThreadExecutor().execute {
            Thread.sleep(SPLASH_SCREEN_INTERVAL)
            splashScreen.setKeepOnScreenCondition() {
                false
            }
        }
//        var isHideSplashScreen = false
//        object : CountDownTimer(CD_TIME, CD_INTERVAL) {
//            override fun onTick(millisUntilFinished: Long) {}
//            override fun onFinish() {
//                isHideSplashScreen = true
//            }
//        }.start()
//        val content: View = activity.findViewById(android.R.id.content)
//        content.viewTreeObserver.addOnPreDrawListener(
//            object : ViewTreeObserver.OnPreDrawListener {
//                override fun onPreDraw(): Boolean {
//                    return if (isHideSplashScreen) {
//                        content.viewTreeObserver.removeOnPreDrawListener(this)
//                        true
//                    } else {
//                        false
//                    }
//                }
//            }
//        )
    }
}