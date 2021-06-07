package com.app.manqla.repository


import com.app.manqla.network.ServiceApi
import com.app.manqla.network.response.article.ArticleResponse
import com.app.manqla.network.response.articles.ArticlesResponse
import com.app.manqla.network.response.like.LikeResponse
import com.app.manqla.network.response.login.LoginResponse
import com.app.manqla.network.response.mubadraat.MubadraatResponse
import com.app.manqla.network.response.questions.QuestionsResponse
import com.app.manqla.network.response.signup.SignUpResponse
import com.app.manqla.network.response.websites.WebsiteResponse
import io.reactivex.Flowable
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val serviceApi: ServiceApi) {

    fun signUp(myMap: Map<String, String>): Flowable<SignUpResponse> {
        return serviceApi.signUp(myMap)
    }

    fun login(myMap: Map<String, String>): Flowable<LoginResponse> {
        return serviceApi.login(myMap)
    }

    fun getArticles(token: String?): Flowable<ArticlesResponse> {
        return serviceApi.getArticles("Bearer $token")
    }

    fun getArticle(token: String?, id: Int): Flowable<ArticleResponse> {
        return serviceApi.getArticle("Bearer $token", id)
    }


    fun addLike(token: String?, id: Int): Flowable<LikeResponse> {
        return serviceApi.addLike("Bearer $token", id)
    }

    fun getQuestions(token: String?): Flowable<QuestionsResponse> {
        return serviceApi.getQuestions("Bearer $token")
    }

    fun getMubadraat(token: String?, category: String): Flowable<MubadraatResponse> {
        return serviceApi.getMubadraat("Bearer $token", category)
    }


    fun getWebsites(token: String?, type: String, page: Int): Flowable<WebsiteResponse> {
        return serviceApi.getWebsites("Bearer $token", type, page)
    }

//    fun setSpin(myMap: Map<String, Int>, token: String): Flowable<SetSpinResponse> {
//        return serviceApi.setSpin(myMap, "Bearer $token")
//    }
//
//    fun withdraw(myMap: Map<String, Any>, token: String): Flowable<WithdrawResponse> {
//        return serviceApi.withdraw(myMap, "Bearer $token")
//    }
//
//    fun getWithdrawList(token: String?): Flowable<GetLastWithdrawResponse> {
//        return serviceApi.getWithdrawList("Bearer $token")
//    }
//
//    fun paymentMethods(token: String?): Flowable<PaymentMethodsResponse> {
//        return serviceApi.paymentMethods("Bearer $token")
//    }
//
//    fun getReferralList(token: String?): Flowable<GetReferralsResponse> {
//        return serviceApi.getReferralList("Bearer $token")
//    }
//
//    fun submitList(phone: String, password: String): Flowable<String> {
//        return serviceApi.submitList(phone, password)
//    }

}