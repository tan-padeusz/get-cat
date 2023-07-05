package pl.tanpadeusz.getcat

import android.app.Application
import timber.log.Timber

class CatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(this.tree)
    }

    private val tree = object : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            super.log(priority, "tbc-$tag", message, t)
        }
    }
}