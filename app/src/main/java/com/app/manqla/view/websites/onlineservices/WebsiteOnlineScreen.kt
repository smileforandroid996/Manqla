package com.app.manqla.view.websites.onlineservices

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faucet.utils.ViewState
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.network.response.websites.Data
import com.app.manqla.network.response.websites.WebsiteResponse
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.articles.ArticlesAdapter
import com.app.manqla.view.articles.details.ArticlesDetailsScreen
import com.app.manqla.view.websites.WebSiteDetailsScreen
import com.app.manqla.view.websites.student.WebsiteStudentViewModel
import dagger.android.support.DaggerAppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_articles_screen.*
import javax.inject.Inject

class WebsiteOnlineScreen : DaggerAppCompatActivity() {

    private var pageNumber = 1
    private var isLoading = true
    private var pastVisibleItem: Int = 0
    var visibleItemsCount: Int = 0
    var totalItemsCount: Int = 0
    var previousTotal = 0
    private var threshold = 2
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var websiteStudentViewModel: WebsiteStudentViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialog: AlertDialog

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var mAdapter: WebsiteOnlineAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_website_online_screen)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "خدمات الكترونية"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)

        mAdapter = WebsiteOnlineAdapter()
        mLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = mAdapter

        mAdapter.onItemClickListener = object :
            WebsiteOnlineAdapter.OnItemClickListener {
            override fun setOnItemClickListener(data: Data) {
                val intent = Intent(this@WebsiteOnlineScreen, WebSiteDetailsScreen::class.java)
                intent.putExtra("name",data.name)
                intent.putExtra("des",data.description)
                intent.putExtra("date",data.createdAt)
                intent.putExtra("image",data.image)
                intent.putExtra("link",data.link)
                startActivity(intent)
            }
        }
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()
        configViewModel()
        getMubadraat()

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                visibleItemsCount = mLayoutManager.childCount
                totalItemsCount = mLayoutManager.itemCount
                pastVisibleItem = mLayoutManager.findFirstVisibleItemPosition()

                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemsCount > previousTotal) { // da el stopping condition, el condition da 3shan a turn on el isloading lma el items tzed m3na kda en fe data gt tany
                            isLoading = false
                            previousTotal = totalItemsCount
                        }
                    }

                    if (!isLoading && (totalItemsCount - visibleItemsCount) <= (pastVisibleItem + threshold)) { // el data gt w el unseen items b2t <= el threshold
                        pageNumber += 1
                        isLoading = true
                        getMubadraat()
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

    private fun configViewModel() {
        websiteStudentViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(WebsiteStudentViewModel::class.java)
    }

    private fun getMubadraat() {

        //electronic_services
        websiteStudentViewModel.getWebsites(sharedPreferences.getString(Constants.ACCESS_TOKEN,"")!!, "electronic_services",pageNumber).observe(this,
            Observer<ViewState<WebsiteResponse>>
            { viewState ->
                when (viewState.status) {
                    ViewState.Status.LOADING -> {
                        dialog.show()
                    }

                    ViewState.Status.SUCCESS -> {
                        dialog.dismiss()
                        if (viewState.data!!.success == true) {
                            mAdapter.submitList(viewState.data.sites?.data)
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
}