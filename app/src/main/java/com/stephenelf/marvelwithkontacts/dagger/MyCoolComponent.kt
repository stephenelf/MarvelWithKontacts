package com.stephenelf.marvelwithkontacts.dagger

import com.stephenelf.marvelwithkontacts.MainActivity
import com.stephenelf.marvelwithkontacts.MyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class, DatabaseModule::class, RepositoryModule::class])
interface MyCoolComponent {
     fun inject(myApplication: MyApplication)

     fun inject(mainActivity: MainActivity)

}