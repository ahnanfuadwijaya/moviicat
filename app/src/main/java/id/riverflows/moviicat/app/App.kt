package id.riverflows.moviicat.app

import android.app.Application
import android.net.Uri
import id.riverflows.moviicat.BuildConfig
import timber.log.Timber

class App: Application() {
    companion object {
        val DRAWABLE_URI: Uri = Uri.parse("android.resource://id.riverflows.moviicat.app/drawable")
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}