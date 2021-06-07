package com.app.manqla.view.mobadrat.onlineservice

import androidx.lifecycle.ViewModel
import com.app.manqla.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MubadraatOnlineViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MubadraatOnlineViewModel::class)
    abstract fun bindsViewModel(viewModel: MubadraatOnlineViewModel): ViewModel
}