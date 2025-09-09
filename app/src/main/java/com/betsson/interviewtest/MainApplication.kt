package com.betsson.interviewtest

import android.app.Application
import com.varosyan.connector.koin.dataModule
import com.varosyan.connector.koin.domainModule
import com.varosyan.connector.koin.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            loadKoinModules(listOf(dataModule(), domainModule(), presenterModule()))
        }
    }
}