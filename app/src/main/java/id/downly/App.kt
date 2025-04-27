package id.downly

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @Author Ahmad Pahmi Created on July 2024
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimberDebug()
    }

    private fun initTimberDebug() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}