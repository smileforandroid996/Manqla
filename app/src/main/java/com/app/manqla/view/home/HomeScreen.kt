package com.app.manqla.view.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.articles.ArticlesScreen
import com.app.manqla.view.contactus.ContactUsScreen
import com.app.manqla.view.goals.GoalsScreen
import com.app.manqla.view.login.LoginScreen
import com.app.manqla.view.mobadrat.MobadratActivity
import com.app.manqla.view.questionsandanswers.QuestionAnswerScreen
import com.app.manqla.view.websites.WebsitesScreen
import com.app.manqla.view.whous.AboutUsScreen
import com.google.android.material.navigation.NavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_content.*
import javax.inject.Inject


class HomeScreen : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val menu: Menu = navigationView.menu

        val logout: MenuItem = menu.findItem(R.id.logout)

        if (Constants.isGuest){
            logout.title = "تسجيل دخول"
        }

        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp)

        first_item.setOnClickListener {
            startActivity(Intent(this, MobadratActivity::class.java))

        }

        second_item.setOnClickListener {
            startActivity(Intent(this, WebsitesScreen::class.java))

        }

        third_item.setOnClickListener {
            startActivity(Intent(this, ArticlesScreen::class.java))

        }
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mubardrat -> {
                startActivity(Intent(this, MobadratActivity::class.java))
            }
            R.id.website -> {
                startActivity(Intent(this, WebsitesScreen::class.java))
            }
            R.id.articles -> {
                startActivity(Intent(this, ArticlesScreen::class.java))
            }
            R.id.who_us -> startActivity(Intent(this, AboutUsScreen::class.java))
            R.id.questions -> startActivity(Intent(this, QuestionAnswerScreen::class.java))
            R.id.contact_us -> startActivity(Intent(this, ContactUsScreen::class.java))
            R.id.vision -> {
                startActivity(Intent(this, GoalsScreen::class.java))
            }
            R.id.share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=com.app.manqla"
                )
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
            R.id.logout -> {
                sharedPreferences.edit().clear().apply()
                startActivity(Intent(this, LoginScreen::class.java))
                finish()
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}