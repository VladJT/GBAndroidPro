package jt.projects.core.splash_screen

import android.app.Activity

fun Activity.showSplashScreen(typeOfSplashScreen: () -> ISplashScreen) {
    val splashScreen = typeOfSplashScreen.invoke()
    splashScreen.init(this)
    splashScreen.show()
}
