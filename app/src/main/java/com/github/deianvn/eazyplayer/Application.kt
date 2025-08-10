package com.github.deianvn.eazyplayer

import com.github.deianvn.eazyplayer.di.ApplicationModule
import com.github.deianvn.eazyplayer.di.BusinessModule
import com.github.deianvn.eazyplayer.di.CoroutineModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@Application)
            modules(
                listOf(ApplicationModule, CoroutineModule, BusinessModule)
            )
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
