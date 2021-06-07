package com.app.manqla.view.websites.student

import androidx.lifecycle.ViewModel
import com.app.manqla.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WebsiteStudentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WebsiteStudentViewModel::class)
    abstract fun bindsViewModel(viewModel: WebsiteStudentViewModel): ViewModel
}