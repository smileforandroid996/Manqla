package com.app.manqla.view.questionsandanswers

import android.app.AlertDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.faucet.utils.ViewState
import com.app.manqla.R
import com.app.manqla.data.Constants
import com.app.manqla.network.response.articles.ArticlesResponse
import com.app.manqla.network.response.questions.QuestionsResponse
import com.app.manqla.utils.LocaleHelper
import com.app.manqla.view.articles.ArticlesAdapter
import com.app.manqla.view.articles.ArticlesViewModel
import dagger.android.support.DaggerAppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_question_answer_screen.*
import javax.inject.Inject

class QuestionAnswerScreen : DaggerAppCompatActivity() {

    private lateinit var mAdapter: QuestionsAdapter
    @Inject
    lateinit var questionsViewModel: QuestionsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var dialog: AlertDialog

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer_screen)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "الاسئلة المتكررة"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_chevron_right_24)


        mAdapter = QuestionsAdapter()
        answer_list.setOnGroupExpandListener(object : ExpandableListView.OnGroupExpandListener {
            var previousGroup = -1
            override fun onGroupExpand(groupPosition: Int) {
                if (groupPosition != previousGroup) answer_list.collapseGroup(previousGroup)
                previousGroup = groupPosition
            }
        })

        answer_list.setAdapter(mAdapter)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()
        configViewModel()
        getArticles()
    }

    private fun configViewModel() {
        questionsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(QuestionsViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        LocaleHelper().setLocale(this, "ar")
    }

    private fun getArticles() {

        questionsViewModel.getQuestions(sharedPreferences.getString(Constants.ACCESS_TOKEN,"")!!).observe(this,
            Observer<ViewState<QuestionsResponse>>
            { viewState ->
                when (viewState.status) {
                    ViewState.Status.LOADING -> {
                        dialog.show()
                    }

                    ViewState.Status.SUCCESS -> {
                        dialog.dismiss()
                        if (viewState.data!!.success == true) {
                            mAdapter.submitList(viewState.data.rows)
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