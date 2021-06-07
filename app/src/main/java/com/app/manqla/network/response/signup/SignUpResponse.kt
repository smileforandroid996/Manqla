package com.app.manqla.network.response.signup


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: Data?
)