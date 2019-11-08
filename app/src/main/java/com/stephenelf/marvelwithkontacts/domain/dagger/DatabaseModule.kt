package com.stephenelf.marvelwithkontacts.domain.dagger

import android.app.Application
import androidx.room.Room
import com.stephenelf.marvelwithkontacts.data.database.MyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MyDatabase  = Room.databaseBuilder(
                application,
                MyDatabase::class.java, "my_database.db"
            )
                .fallbackToDestructiveMigration()
                .build()
}