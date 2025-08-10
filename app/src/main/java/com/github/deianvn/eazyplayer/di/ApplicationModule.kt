package com.github.deianvn.eazyplayer.di

import com.github.deianvn.creditplanner.utils.serialization.MoshiLocalDateTimeAdapter
import com.squareup.moshi.Moshi
import org.koin.dsl.module


val ApplicationModule = module {

    single {
        Moshi.Builder()
            .add(MoshiLocalDateTimeAdapter())
            .build()
    }

}
