package com.github.deianvn.eazyplayer.di

import com.github.deianvn.eazyplayer.utils.storage.SharedPrefStorage
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val BusinessModule = module {

    single {
        SharedPrefStorage(androidApplication(), get())
    }

}
