package com.app.manqla.view.login

import androidx.lifecycle.ViewModel
import com.app.manqla.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsViewModel(viewModel: LoginViewModel): ViewModel
}