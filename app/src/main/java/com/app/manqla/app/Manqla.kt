package com.app.manqla.app

import com.app.manqla.di.AppComponent
import com.app.manqla.di.DaggerAppComponent
import dagger.android.DaggerApplication

class Manqla: DaggerApplication(){

    override fun applicationInjector(): AppComponent {
        return DaggerAppComponent.builder().application(this).build()
    }

}