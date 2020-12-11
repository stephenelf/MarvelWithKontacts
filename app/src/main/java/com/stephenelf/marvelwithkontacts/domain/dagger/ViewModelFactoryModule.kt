package com.stephenelf.marvelwithkontacts.domain.dagger

import androidx.lifecycle.ViewModelProvider
import com.stephenelf.marvelwithkontacts.ui.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}