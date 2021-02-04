package com.rixspi.framework.di

import com.rixspi.presentation.NotesViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    fun helloViewModelFactory(factory: NotesViewModel.Factory): AssistedViewModelFactory<*, *>
}
