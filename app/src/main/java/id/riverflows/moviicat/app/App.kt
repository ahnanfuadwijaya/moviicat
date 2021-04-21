package id.riverflows.moviicat.app

import android.app.Application
import android.net.Uri
import id.riverflows.moviicat.BuildConfig
import id.riverflows.moviicat.util.DataDummy
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}