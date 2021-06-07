package com.app.manqla.view.mobadrat

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.manqla.R
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.articles.details.ArticleDetailsViewModel
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_articles_details_screen.*
import kotlinx.android.synthetic.main.activity_mubadrat_details_screen.*
import kotlinx.android.synthetic.main.activity_mubadrat_details_screen.article_body
import kotlinx.android.synthetic.main.activity_mubadrat_details_screen.article_title
import kotlinx.android.synthetic.main.activity_mubadrat_details_screen.date
import kotlinx.android.synthetic.main.activity_mubadrat_details_screen.image
import javax.inject.Inject

class MubadratDetailsScreen : YouTubeBaseActivity() {

    @Inject
    lateinit var articleDetailsViewModel: ArticleDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialog: AlertDialog

    private var video: String = "Ar3o4NuAeVw"
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mubadrat_details_screen)


        youtube.initialize("AIzaSyBocAK5NqXTfsijXcLEJacK57GmrCK-P-M", object: com.google.android.youtube.player.YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: com.google.android.youtube.player.YouTubePlayer.Provider?,
                p1: com.google.android.youtube.player.YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(intent.getStringExtra("video"))
                p1?.pause()
            }

            override fun onInitializationFailure(
                p0: com.google.android.youtube.player.YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@MubadratDetailsScreen,"Error", Toast.LENGTH_LONG).show()
            }

        })
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.title = intent.getStringExtra("title")
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)

//        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)
        date.text = intent.getStringExtra("date")
        article_title.text = intent.getStringExtra("title")
        toolbar_title.text = intent.getStringExtra("title")
        article_body.text = intent.getStringExtra("des")
        Picasso.get().load(intent.getStringExtra("image")).into(image)
//        video = intent.getStringExtra("video").toString()

        back_btn.setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}