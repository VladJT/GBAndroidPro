package jt.projects.core.splash_screen

import android.animation.ObjectAnimator
import android.os.Build
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd

const val SLIDE_LEFT_DURATION = 1000L

class AnimatedSplashScreen(private val splashScreen: ISplashScreen = DefaultSplashScreen()) :
    ISplashScreen by splashScreen {

    override fun show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setSplashScreenHideAnimation()
        }
        splashScreen.show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun setSplashScreenHideAnimation() {
        activity.splashScreen.setOnExitAnimationListener { splashScreenView ->
            ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.height.toFloat()
            ).apply {
                interpolator = AnticipateInterpolator()
                duration = SLIDE_LEFT_DURATION
                doOnEnd { splashScreenView.remove() }
            }
                .start()
        }
    }
}