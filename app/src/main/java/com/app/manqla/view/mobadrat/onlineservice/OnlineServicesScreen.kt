package com.app.manqla.view.mobadrat.onlineservice

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
import com.app.faucet.utils.ViewState
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.network.response.mubadraat.Data
import com.app.manqla.network.response.mubadraat.MubadraatResponse
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.articles.details.ArticlesDetailsScreen
import com.app.manqla.view.mobadrat.MubadratDetailsScreen
import com.app.manqla.view.mobadrat.mobadratstudent.MobadratStudentAdapter
import com.app.manqla.view.mobadrat.mobadratstudent.MubadraatStudentViewModel
import dagger.android.support.DaggerAppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_articles_screen.*
import javax.inject.Inject

class OnlineServicesScreen : DaggerAppCompatActivity() {

    @Inject
    lateinit var mubadraatOnlineViewModel: MubadraatOnlineViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialog: AlertDialog

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var mAdapter: OnlineServicesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_services_screen)
        LocaleHelper().setLocale(this, "ar")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "خدمات الكترونية"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)

        mAdapter = OnlineServicesAdapter()
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = mAdapter

        mAdapter.onItemClickListener = object :
            OnlineServicesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(data: Data) {
                val intent = Intent(this@OnlineServicesScreen, MubadratDetailsScreen::class.java)
                intent.putExtra("title",data.title)
                intent.putExtra("des",data.description)
                intent.putExtra("date",data.createdAt)
                intent.putExtra("image",data.image)
                intent.putExtra("video",data.video)
                startActivity(intent)
            }
        }

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()
        configViewModel()
        getMubadraat()
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

    private fun configViewModel() {
        mubadraatOnlineViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MubadraatOnlineViewModel::class.java)
    }

    private fun getMubadraat() {

        //electronic_services
        mubadraatOnlineViewModel.getMubadraat(sharedPreferences.getString(Constants.ACCESS_TOKEN,"")!!, "electronic_services").observe(this,
            Observer<ViewState<MubadraatResponse>>
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