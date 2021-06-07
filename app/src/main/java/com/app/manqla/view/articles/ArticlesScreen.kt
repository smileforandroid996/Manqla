package com.app.manqla.view.articles

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.app.faucet.utils.ViewState
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.network.response.articles.ArticlesResponse
import com.app.manqla.network.response.login.LoginResponse
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.articles.details.ArticlesDetailsScreen
import com.app.manqla.view.home.HomeScreen
import com.app.manqla.view.login.LoginViewModel
import dagger.android.support.DaggerAppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_articles_screen.*
import kotlinx.android.synthetic.main.activity_login_screen.*
import javax.inject.Inject

class ArticlesScreen : DaggerAppCompatActivity() {

    private lateinit var mAdapter:ArticlesAdapter
    @Inject
    lateinit var articlesViewModel: ArticlesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialog: AlertDialog

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_screen)
        LocaleHelper().setLocale(this, "ar")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "مقالات"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)

        mAdapter = ArticlesAdapter()
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = mAdapter

        mAdapter.onItemClickListener = object :
            ArticlesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                val intent = Intent(this@ArticlesScreen, ArticlesDetailsScreen::class.java)
                intent.putExtra(Constants.ARTICLE_ID,id)
                startActivity(intent)
            }
        }
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()
        configViewModel()
        getArticles()
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

    private fun configViewModel() {
        articlesViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ArticlesViewModel::class.java)
    }

    fun getArticles() {

        articlesViewModel.getArticles(sharedPreferences.getString(Constants.ACCESS_TOKEN,"")!!).observe(this,
            Observer<ViewState<ArticlesResponse>>
            { viewState ->
                when (viewState.status) {
                    ViewState.Status.LOADING -> {
                        dialog.show()
                    }

                    ViewState.Status.SUCCESS -> {
                        dialog.dismiss()
                        if (viewState.data!!.success == true) {
                            mAdapter.submitList(viewState.data.invitations?.data)
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}