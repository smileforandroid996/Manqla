package com.app.manqla.view.signup

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.faucet.utils.ViewState
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.network.response.signup.SignUpResponse
import com.app.manqla.view.home.HomeScreen
import com.app.manqla.view.login.LoginScreen
import dagger.android.support.DaggerAppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_sign_up_screen.*

import okhttp3.internal.and
import java.net.NetworkInterface
import java.util.*
import javax.inject.Inject

class SignUpScreen : DaggerAppCompatActivity() {

    private var fullname: String = ""

    @Inject
    lateinit var signUpViewModel: SignUpViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialog: AlertDialog

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var phone: String = ""
    private var username: String = ""
    private var password: String = ""
    private var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()

        configViewModel()
    }

    private fun configViewModel() {
        signUpViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)
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
        startActivity(Intent(this, LoginScreen::class.java))
        finish()
    }

    fun signUp(view: View) {
        username = et_username.text.toString().trim()
        fullname = et_fullname.text.toString().trim()
        password= et_password.text.toString()
        email = et_email.text.toString().trim()
        phone = et_phone.text.toString().trim()

        when {
            username.isEmpty() -> {
                Toast.makeText(this, "Please Enter Username", Toast.LENGTH_LONG).show()
                return
            }
            fullname.isEmpty() -> {
                Toast.makeText(this, "Please Enter Full Name", Toast.LENGTH_LONG).show()
                return
            }
            email.isEmpty() -> {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_LONG).show()
                return
            }
            password.isEmpty() -> {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show()
                return
            }

        }
//        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//        val wInfo = wifiManager.connectionInfo
        val macAddress = getMacAddr()

        val myMap: Map<String, String> = mapOf(
            "user_name" to username,
            "full_name" to fullname,
            "email" to email,
            "phone" to phone,
            "password" to password,
            "password_confirmation" to password
        )

        signUpViewModel.signUp(myMap).observe(this,
            Observer<ViewState<SignUpResponse>>
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
                            Toast.makeText(baseContext, "Error", Toast.LENGTH_LONG).show()
                        }
                    }

                    ViewState.Status.ERROR -> {
                        dialog.dismiss()
                        Toast.makeText(baseContext, "${viewState.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
}

    private fun getMacAddr(): String {
        try {
            val all: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif: NetworkInterface in all) {
                if (!nif.name.equals("wlan0", true)) continue

                val macBytes: ByteArray = nif.hardwareAddress
                if (macBytes == null) {
                    return ""
                }
                val res1 = StringBuilder()
                for (b: Byte in macBytes) {
                    res1.append(Integer.toHexString(b and 0xFF) + ":")
                }
                if (res1.length > 0) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: Exception) {
            //handle exception
        }
        return ""
    }
}