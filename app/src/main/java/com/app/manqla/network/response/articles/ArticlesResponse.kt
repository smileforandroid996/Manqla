package com.app.manqla.network.response.articles


import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("invitations")
    val invitations: Invitations?,
    @SerializedName("success")
    val success: Boolean?
)