package com.app.manqla.view.articles.details

import androidx.lifecycle.ViewModel
import com.app.manqla.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ArticleDetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ArticleDetailsViewModel::class)
    abstract fun bindsViewModel(viewModel: ArticleDetailsViewModel): ViewModel
}