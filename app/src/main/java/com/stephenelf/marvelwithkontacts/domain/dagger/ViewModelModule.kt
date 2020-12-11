package com.stephenelf.marvelwithkontacts.domain.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stephenelf.marvelwithkontacts.ui.ContactsViewModel
import com.stephenelf.marvelwithkontacts.ui.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

/*

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
*/

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    internal abstract fun postContactsViewModel(viewModel: ContactsViewModel): ViewModel

    //Add more ViewModels here
}