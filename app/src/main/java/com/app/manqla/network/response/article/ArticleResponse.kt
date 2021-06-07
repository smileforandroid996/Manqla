package com.app.manqla.network.response.article


import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("blog")
    val blog: Blog?,
    @SerializedName("success")
    val success: Boolean?
)