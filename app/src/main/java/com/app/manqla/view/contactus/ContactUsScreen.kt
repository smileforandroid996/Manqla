package com.app.manqla.view.contactus

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.manqla.R
import com.app.manqla.utils.LocaleHelper
import com.google.android.youtube.player.YouTubeIntents





class ContactUsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us_screen)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "تواصل معنا"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun facebook(view: View) {
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        val facebookUrl: String = getOpenFacebookIntent()
        facebookIntent.data = Uri.parse(facebookUrl)
        startActivity(facebookIntent)
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

    fun instagram(view: View) {
        val uri = Uri.parse("http://instagram.com/_u/man2.la")
        val likeIng = Intent(Intent.ACTION_VIEW, uri)

        likeIng.setPackage("com.instagram.android")

        try {
            startActivity(likeIng)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/man2.la")
                )
            )
        }
    }

    fun email(view: View) {
        sendMail(this, "digitalmanqla@gmail.com", "Support")
    }

    fun youtube(view: View) {
        val intent = YouTubeIntents.createChannelIntent(this, "UCp2gF7tI_rkA0Q3xWH8gEGg")
        startActivity(intent)
    }

    private fun sendMail(context: Context, email: String, shareTitle: String?) {
        try {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            context.startActivity(emailIntent)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    ///https://www.facebook.com/Man2la2020
    private val FACEBOOK_URL = "https://www.facebook.com/Man2la2020"
    private val FACEBOOK_PAGE_ID = "Man2la2020"

    private fun getOpenFacebookIntent(): String {
        val packageManager: PackageManager = packageManager
        return try {
            val versionCode =
                packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            if (versionCode >= 3002850) { //newer versions of fb app
                "fb://facewebmodal/f?href=$FACEBOOK_URL"
            } else { //older versions of fb app
                "fb://page/$FACEBOOK_PAGE_ID"
            }
        } catch (e: PackageManager.NameNotFoundException) {
            FACEBOOK_URL //normal web url
        }
    }

    private fun call(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        try {
            startActivity(intent)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun whatsApp(view: View?) {
        val number = "0123456789"
        try {
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net")
            startActivity(sendIntent)
        } catch (e: Exception) {
            Log.e("Error", "ERROR_OPEN_MESSANGER$e")
        }
    }

    fun call(view: View) {
        call("+0123456789")
    }

}