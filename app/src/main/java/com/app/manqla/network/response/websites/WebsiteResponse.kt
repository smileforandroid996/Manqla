package com.app.manqla.network.response.websites


import com.google.gson.annotations.SerializedName

data class WebsiteResponse(
    @SerializedName("sites")
    val sites: Sites?,
    @SerializedName("success")
    val success: Boolean?
)