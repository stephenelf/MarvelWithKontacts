package com.stephenelf.marvelwithkontacts.dagger

import android.app.Application
import com.stephenelf.marvelwithkontacts.database.MyDatabase
import com.stephenelf.marvelwithkontacts.net.MarvelAPI
import com.stephenelf.marvelwithkontacts.repositories.ContactsRepository
import com.stephenelf.marvelwithkontacts.repositories.LocalRepository
import com.stephenelf.marvelwithkontacts.repositories.RemoteRepository
import com.stephenelf.marvelwithkontacts.repositories.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesRepository(
        application: Application,
        localRepository: LocalRepository,
        remoteRepository: RemoteRepository,
        contactsRepository: ContactsRepository
    ): Repository = Repository(application, localRepository, remoteRepository, contactsRepository)


    @Provides
    @Singleton
    fun providesContactRepository(): ContactsRepository = ContactsRepository()


    @Provides
    @Singleton
    fun providesLocalRepository(database: MyDatabase): LocalRepository =
        LocalRepository(database)


    @Provides
    @Singleton
    fun providesRemoteRepository(application: Application, marvelAPI: MarvelAPI): RemoteRepository =
        RemoteRepository(application, marvelAPI)

}