package com.rixspi.notes.framework.di

import com.rixspi.common.framework.di.AssistedViewModelFactory
import com.rixspi.common.framework.di.MavericksViewModelComponent
import com.rixspi.common.framework.di.ViewModelKey
import com.rixspi.notes.presentation.NotesViewModel
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
