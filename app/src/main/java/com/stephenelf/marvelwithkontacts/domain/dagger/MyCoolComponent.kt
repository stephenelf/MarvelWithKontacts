package com.stephenelf.marvelwithkontacts.domain.dagger

import com.stephenelf.marvelwithkontacts.ui.MainActivity
import com.stephenelf.marvelwithkontacts.MyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class, DatabaseModule::class, RepositoryModule::class])
interface MyCoolComponent {
     fun inject(myApplication: MyApplication)

     fun inject(mainActivity: MainActivity)

}