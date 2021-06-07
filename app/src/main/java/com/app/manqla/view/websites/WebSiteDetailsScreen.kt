package com.app.manqla.view.websites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.manqla.R
import com.app.manqla.utils.LocaleHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_web_site_details_screen.*


class WebSiteDetailsScreen : AppCompatActivity() {

    private var link: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_site_details_screen)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("name")
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)

        link = intent.getStringExtra("link")!!
        name.text = intent.getStringExtra("name")
        des.text = intent.getStringExtra("des")
        Picasso.get().load(intent.getStringExtra("image")).into(image)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

    fun openWebSite(view: View) {
        if (link.isNotEmpty()){
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(browserIntent)
        }
    }
}