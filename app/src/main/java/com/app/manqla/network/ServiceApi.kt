package com.app.manqla.network


import com.app.manqla.network.response.article.ArticleResponse
import com.app.manqla.network.response.articles.ArticlesResponse
import com.app.manqla.network.response.like.LikeResponse
import com.app.manqla.network.response.login.LoginResponse
import com.app.manqla.network.response.mubadraat.MubadraatResponse
import com.app.manqla.network.response.questions.QuestionsResponse
import com.app.manqla.network.response.signup.SignUpResponse
import com.app.manqla.network.response.websites.WebsiteResponse
import io.reactivex.Flowable
import retrofit2.http.*

@JvmSuppressWildcards
interface ServiceApi {

    @FormUrlEncoded
    @POST("user/register")
    fun signUp(@FieldMap myMap: Map<String, String>
    ): Flowable<SignUpResponse>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@FieldMap myMap: Map<String, String>
    ): Flowable<LoginResponse>

    @GET("user/blogs")
    fun getArticles(@Header("Authorization") token: String?
    ): Flowable<ArticlesResponse>

    @GET("user/blog")
    fun getArticle(@Header("Authorization") token: String?, @Query("blog_id") id: Int): Flowable<ArticleResponse>

    @POST("user/like-blog")
    fun addLike(@Header("Authorization") token: String?, @Query("blog_id") id: Int): Flowable<LikeResponse>

    @GET("user/faqs")
    fun getQuestions(@Header("Authorization") token: String?
    ): Flowable<QuestionsResponse>

    @GET("user/invitations/category")
    fun getMubadraat(@Header("Authorization") token: String?, @Query("category") category: String): Flowable<MubadraatResponse>

    @GET("user/sites/type")
    fun getWebsites(@Header("Authorization") token: String?, @Query("type") type: String, @Query("page") page: Int): Flowable<WebsiteResponse>

//    @GET("user/withdraw-requests")
//    fun getWithdrawList(@Header("Authorization") token: String?
//    ): Flowable<GetLastWithdrawResponse>
//
//    @GET("user/payment-methods")
//    fun paymentMethods(@Header("Authorization") token: String?
//    ): Flowable<PaymentMethodsResponse>
//
//    @GET("user/get-referrals")
//    fun getReferralList(@Header("Authorization") token: String?
//    ): Flowable<GetReferralsResponse>
//
//    @FormUrlEncoded
//    @POST("user/spin")
//    fun setSpin(@FieldMap myMap: Map<String, Int>, @Header("Authorization") token: String
//    ): Flowable<SetSpinResponse>
//
//    @FormUrlEncoded
//    @POST("user/withdraw")
//    fun withdraw(@FieldMap myMap: Map<String, Any>, @Header("Authorization") token: String
//    ): Flowable<WithdrawResponse>
//
//
//    @FormUrlEncoded
//    @POST("user/register")
//    fun submitList(
//        @Field("username") phone: String,
//        @Field("password") password: String
//    ): Flowable<String>

}