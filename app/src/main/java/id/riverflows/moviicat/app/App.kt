package id.riverflows.moviicat.app

import android.app.Application
import id.riverflows.moviicat.BuildConfig
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}