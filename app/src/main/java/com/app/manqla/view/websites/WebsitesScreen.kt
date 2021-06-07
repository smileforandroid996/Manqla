package com.app.manqla.view.websites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.articles.details.ArticlesDetailsScreen
import com.app.manqla.view.websites.onlineservices.WebsiteOnlineScreen
import com.app.manqla.view.websites.student.WebsiteStudentScreen

class WebsitesScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_websites_screen)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "مواقع التحول الرقمى"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

    fun student(view: View) {
        val intent = Intent(this, WebsiteStudentScreen::class.java)
        startActivity(intent)
    }

    fun onlineServices(view: View) {
        val intent = Intent(this, WebsiteOnlineScreen::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}