package com.app.manqla.view.articles.details

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.faucet.utils.ViewState
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.network.response.article.ArticleResponse
import com.app.manqla.network.response.like.LikeResponse
import com.app.manqla.utils.LocaleHelper
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_articles_details_screen.*
import javax.inject.Inject


class ArticlesDetailsScreen : DaggerAppCompatActivity() {

    @Inject
    lateinit var articleDetailsViewModel: ArticleDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialog: AlertDialog

    private var id: Int = 0
    private var shareLink: String = ""
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_details_screen)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)

        share.setOnClickListener {
            if (shareLink.isNotEmpty() && shareLink.isNotBlank()){
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my Article: $shareLink")
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
        }

        like.setOnClickListener {
            if (!Constants.isGuest){
                addLike()
            }else{
                Toast.makeText(this, "Please Login First", Toast.LENGTH_LONG).show()
            }
        }
        id = intent.getIntExtra(Constants.ARTICLE_ID, 0)
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()
        configViewModel()
        if (id != 0){
            getArticles()
        }
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

    private fun configViewModel() {
        articleDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            ArticleDetailsViewModel::class.java
        )
    }

    private fun getArticles() {

        articleDetailsViewModel.getArticle(
            sharedPreferences.getString(
                Constants.ACCESS_TOKEN,
                ""
            )!!, id
        ).observe(this,
            Observer<ViewState<ArticleResponse>>
            { viewState ->
                when (viewState.status) {
                    ViewState.Status.LOADING -> {
                        dialog.show()
                    }

                    ViewState.Status.SUCCESS -> {
                        dialog.dismiss()
                        if (viewState.data!!.success == true) {
                            supportActionBar!!.title = viewState.data.blog?.title
                            Picasso.get().load(viewState.data.blog?.image).into(image)
                            shareLink = viewState.data.blog?.shareLink!!
                            author.text = "الكاتب : ${viewState.data.blog.authorName}"
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                article_title.text = Html.fromHtml(
                                    viewState.data.blog.title,
                                    Html.FROM_HTML_MODE_COMPACT
                                );
                                article_body.text = Html.fromHtml(
                                    viewState.data.blog.description,
                                    Html.FROM_HTML_MODE_COMPACT
                                );
                            } else {
                                article_title.text = Html.fromHtml(viewState.data.blog.title);
                                article_body.text = Html.fromHtml(viewState.data.blog.description);
                            }
                            date.text = viewState.data.blog.createdAt
                            if (viewState.data.blog.isLike!!) {
                                like.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                            } else {
                                like.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                            }

                        } else {
                            Toast.makeText(baseContext, "Error", Toast.LENGTH_LONG).show()
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

    private fun addLike() {
        articleDetailsViewModel.addLike(
            sharedPreferences.getString(Constants.ACCESS_TOKEN, "")!!,
            id
        ).observe(this,
            Observer<ViewState<LikeResponse>>
            { viewState ->
                when (viewState.status) {
                    ViewState.Status.LOADING -> {
                        dialog.show()
                    }

                    ViewState.Status.SUCCESS -> {
                        dialog.dismiss()
                        if (viewState.data!!.success == true) {
                            like.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                        } else {
                            Toast.makeText(baseContext, "Error", Toast.LENGTH_LONG).show()
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

    fun openBrowser(view: View) {
        try {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(shareLink))
            startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this, "No application can handle this request."
                        + " Please install a webbrowser", Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }
}