package tech.blur.sexsquare

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tech.blur.sexsquare.di.sexsquareApp

class SeqxsquareApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SeqxsquareApp)
            modules(sexsquareApp)
        }
    }
}
