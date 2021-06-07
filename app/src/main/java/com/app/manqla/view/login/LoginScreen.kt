package com.app.manqla.view.login

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.faucet.utils.ViewState
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.network.response.login.LoginResponse
import com.app.manqla.view.home.HomeScreen
import com.app.manqla.view.signup.SignUpScreen
import dagger.android.support.DaggerAppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_login_screen.*
import javax.inject.Inject

class LoginScreen : DaggerAppCompatActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialog: AlertDialog

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var password: String = ""
    private var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()
        configViewModel()
    }

    private fun configViewModel() {
        loginViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    private var isHidden = true
    fun showHidePassword(view: View) {
        if (isHidden) {
            isHidden = false
            et_password.transformationMethod = null
            show_hide.setImageResource(R.drawable.ic_baseline_visibility_24)
        } else {
            isHidden = true
            et_password.transformationMethod = PasswordTransformationMethod()
            show_hide.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        }
    }

    fun login(view: View) {
        password= et_password.text.toString()
        email = et_username.text.toString().trim()

        when {
            email.isEmpty() -> {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_LONG).show()
                return
            }
            password.isEmpty() -> {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show()
                return
            }
        }

        val myMap: Map<String, String> = mapOf(
            "email" to email,
            "password" to password
        )

        loginViewModel.login(myMap).observe(this,
            Observer<ViewState<LoginResponse>>
            { viewState ->
                when (viewState.status) {
                    ViewState.Status.LOADING -> {
                        dialog.show()
                    }

                    ViewState.Status.SUCCESS -> {
                        dialog.dismiss()
                        if (viewState.data!!.code == 200) {
                            sharedPreferences.edit().putString(Constants.ACCESS_TOKEN, viewState.data.data!!.token).apply()
                            sharedPreferences.edit().putString(Constants.FULLNAME, viewState.data.data.user!!.fullName).apply()
                            sharedPreferences.edit().putString(Constants.USERNAME, viewState.data.data.user.userName).apply()
                            sharedPreferences.edit().putString(Constants.EMAIL, viewState.data.data.user.email).apply()
                            sharedPreferences.edit().putString(Constants.PHONE, viewState.data.data.user.phone).apply()
                            sharedPreferences.edit().putString(Constants.USERSTATUS, "true").apply()
                            Constants.isGuest = false
                            startActivity(Intent(this, HomeScreen::class.java))
                            finish()
                        } else {
                            Toast.makeText(baseContext, "Wrong Email or Password", Toast.LENGTH_LONG).show()
                        }
                    }

                    ViewState.Status.ERROR -> {
                        dialog.dismiss()
                        Toast.makeText(baseContext, "${viewState.message}", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        )
    }
    fun signUp(view: View) {
        startActivity(Intent(this, SignUpScreen::class.java))
        finish()
    }

    fun skip(view: View) {
        Constants.isGuest = true
        startActivity(Intent(this, HomeScreen::class.java))
        finish()
    }

}