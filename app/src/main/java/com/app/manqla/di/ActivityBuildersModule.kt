package com.app.manqla.di



import com.app.manqla.view.articles.ArticlesScreen
import com.app.manqla.view.articles.ArticlesViewModelModule
import com.app.manqla.view.articles.details.ArticleDetailsViewModelModule
import com.app.manqla.view.articles.details.ArticlesDetailsScreen
import com.app.manqla.view.home.HomeScreen
import com.app.manqla.view.login.LoginScreen
import com.app.manqla.view.login.LoginViewModelModule
import com.app.manqla.view.mobadrat.mobadratstudent.MobadratStudentScreen
import com.app.manqla.view.mobadrat.mobadratstudent.MubadraatStudentViewModelModule
import com.app.manqla.view.mobadrat.onlineservice.MubadraatOnlineViewModelModule
import com.app.manqla.view.mobadrat.onlineservice.OnlineServicesScreen
import com.app.manqla.view.questionsandanswers.QuestionAnswerScreen
import com.app.manqla.view.questionsandanswers.QuestionsViewModelModule
import com.app.manqla.view.signup.SignUpScreen
import com.app.manqla.view.signup.SignUpViewModelModule
import com.app.manqla.view.splash.SplashScreen
import com.app.manqla.view.websites.onlineservices.WebsiteOnlineScreen
import com.app.manqla.view.websites.student.WebsiteStudentScreen
import com.app.manqla.view.websites.student.WebsiteStudentViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

//    @ContributesAndroidInjector()
//    abstract fun contributeHomeActivity(): HomeScreen

    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun contributeLoginActivity(): LoginScreen

    @ContributesAndroidInjector(modules = [SignUpViewModelModule::class])
    abstract fun contributeSignUpScreen(): SignUpScreen

    @ContributesAndroidInjector(modules = [ArticlesViewModelModule::class])
    abstract fun contributeArticlesScreen(): ArticlesScreen


    @ContributesAndroidInjector(modules = [QuestionsViewModelModule::class])
    abstract fun contributeQuestionAnswerScreen(): QuestionAnswerScreen

    @ContributesAndroidInjector(modules = [ArticleDetailsViewModelModule::class])
    abstract fun contributeArticlesDetailsScreen(): ArticlesDetailsScreen

    @ContributesAndroidInjector(modules = [MubadraatStudentViewModelModule::class])
    abstract fun contributeMobadratStudentScreen(): MobadratStudentScreen

    @ContributesAndroidInjector(modules = [MubadraatOnlineViewModelModule::class])
    abstract fun contributeOnlineServicesScreen(): OnlineServicesScreen

    @ContributesAndroidInjector(modules = [WebsiteStudentViewModelModule::class])
    abstract fun contributeWebsiteOnlineScreen(): WebsiteOnlineScreen

    @ContributesAndroidInjector(modules = [WebsiteStudentViewModelModule::class])
    abstract fun contributeWebsiteStudentScreen(): WebsiteStudentScreen

    @ContributesAndroidInjector()
    abstract fun contributeHomeScreen(): HomeScreen

//    @ContributesAndroidInjector(modules = [SignUpViewModelModule::class])
//    abstract fun contributeSignUpActivity(): SignUpActivity
//
    @ContributesAndroidInjector()
    abstract fun contributeSplashScreenActivity(): SplashScreen
//
//    @ContributesAndroidInjector(modules = [WithdrawViewModelModule::class])
//    abstract fun contributeWithdrawActivity(): WithDrawActivity
//
//    @ContributesAndroidInjector()
//    abstract fun contributeInviteFreindActivvity(): InviteFreindActivvity
}