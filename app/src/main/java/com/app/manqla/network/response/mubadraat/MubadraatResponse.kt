package com.app.manqla.network.response.mubadraat


import com.google.gson.annotations.SerializedName

data class MubadraatResponse(
    @SerializedName("invitations")
    val invitations: Invitations?,
    @SerializedName("success")
    val success: Boolean?
)