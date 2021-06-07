package com.app.manqla.view.mobadrat

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.app.manqla.R
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.mobadrat.mobadratstudent.MobadratStudentScreen
import com.app.manqla.view.mobadrat.onlineservice.OnlineServicesScreen
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_mobadrat.*


class MobadratActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobadrat)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "مبادرات"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)

//        youtube_player_view.addYouTubePlayerListener(object :
//            AbstractYouTubePlayerListener() {
//            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
//                val videoId = "S0Q4gqBUs7c"
//                youTubePlayer.loadVideo(videoId, 0f)
//
//            }
//        })
//        youtube_player_view.enterFullScreen()
    }

    fun mobadratStudent(view: View) {startActivity(Intent(this, MobadratStudentScreen::class.java))}
    fun mobadratOnlineService(view: View) {startActivity(
        Intent(
            this,
            OnlineServicesScreen::class.java
        )
    )}

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