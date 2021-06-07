package com.app.manqla.view.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.home.HomeScreen
import com.app.manqla.view.login.LoginScreen
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import javax.inject.Inject

class SplashScreen : DaggerAppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val myFadeInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fadein)
        iv_logo.startAnimation(myFadeInAnimation)
        Handler().postDelayed({
            if (sharedPreferences.getString(Constants.USERSTATUS, "false") == "true") {
                startActivity(Intent(this@SplashScreen, HomeScreen::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashScreen, LoginScreen::class.java))
                finish()
            }
        }, 5000)
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }
}