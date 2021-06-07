package com.app.manqla.view.mobadrat.mobadratstudent

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
import com.app.manqla.network.response.questions.QuestionsResponse
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.articles.ArticlesAdapter
import com.app.manqla.view.articles.details.ArticlesDetailsScreen
import com.app.manqla.view.mobadrat.MubadratDetailsScreen
import com.app.manqla.view.questionsandanswers.QuestionsAdapter
import com.app.manqla.view.questionsandanswers.QuestionsViewModel
import dagger.android.support.DaggerAppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_articles_screen.*
import javax.inject.Inject

class MobadratStudentScreen : DaggerAppCompatActivity() {

    @Inject
    lateinit var mubadraatStudentViewModel: MubadraatStudentViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialog: AlertDialog

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var mAdapter: MobadratStudentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobadrat_student_screen)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "مبادرات للطلبة و المدرسين"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)

        mAdapter = MobadratStudentAdapter()
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = mAdapter

        mAdapter.onItemClickListener = object :
            MobadratStudentAdapter.OnItemClickListener {
            override fun setOnItemClickListener(data: Data) {
                val intent = Intent(this@MobadratStudentScreen, MubadratDetailsScreen::class.java)
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
        mubadraatStudentViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MubadraatStudentViewModel::class.java)
    }

    private fun getMubadraat() {

        //electronic_services
        mubadraatStudentViewModel.getMubadraat(sharedPreferences.getString(Constants.ACCESS_TOKEN,"")!!, "initiatives_students_teachers").observe(this,
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