package be.ucll.dirkfalls

import android.content.pm.ActivityInfo
import android.os.Bundle
import be.ucll.dirkfalls.facebook.FacebookAndroid

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration


class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        config.useGyroscope = true
        config.useAccelerometer = false
        config.useCompass = false
        initialize(DirkFallsGame(FacebookAndroid(this)), config)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
