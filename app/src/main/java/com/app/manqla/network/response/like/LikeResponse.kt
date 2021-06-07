package com.app.manqla.network.response.like


import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)