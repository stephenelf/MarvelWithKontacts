package com.stephenelf.marvelwithkontacts

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.stephenelf.marvelwithkontacts.dagger.*




class MyApplication : Application() {


    companion object {
        lateinit var INSTANCE: MyApplication
    }


    val coolComponent: MyCoolComponent by lazy {
        DaggerMyCoolComponent.builder()
            .appModule(AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
            .netModule(NetModule(getString(R.string.marvel_base_url)))
            .databaseModule(DatabaseModule())
            .repositoryModule(RepositoryModule())
            .build()
    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

    }



}