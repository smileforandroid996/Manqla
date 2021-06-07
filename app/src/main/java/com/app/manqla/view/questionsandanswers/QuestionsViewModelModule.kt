package com.app.manqla.view.questionsandanswers

import androidx.lifecycle.ViewModel
import com.app.manqla.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class QuestionsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(QuestionsViewModel::class)
    abstract fun bindsViewModel(viewModel: QuestionsViewModel): ViewModel
}