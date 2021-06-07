package com.app.manqla.view.mobadrat.mobadratstudent

import androidx.lifecycle.ViewModel
import com.app.manqla.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MubadraatStudentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MubadraatStudentViewModel::class)
    abstract fun bindsViewModel(viewModel: MubadraatStudentViewModel): ViewModel
}