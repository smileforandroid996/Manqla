package com.app.manqla.di


import android.app.Application
import com.app.manqla.app.Manqla
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ViewModelFactory::class,
            ActivityBuildersModule::class,
            AppModule::class
            ]
)
interface AppComponent : AndroidInjector<Manqla> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}