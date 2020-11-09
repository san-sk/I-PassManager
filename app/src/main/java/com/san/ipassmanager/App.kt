package com.san.ipassmanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {

   /* override fun onCreate() {
        super.onCreate()


        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    retrofitModule,
                    apiModule,
                    sharedPreferencesModule,
                    utilsModule,
                    databaseModule
                )
            )
        }
    }*/
}