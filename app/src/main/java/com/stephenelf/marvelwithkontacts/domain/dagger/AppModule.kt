package com.stephenelf.marvelwithkontacts.domain.dagger

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidInjectionModule::class])
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application = application


    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

}