package com.app.manqla.network.response.login


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("birth_date")
    val birthDate: Any?,
    @SerializedName("country")
    val country: Any?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("mac_addr")
    val macAddr: Any?,
    @SerializedName("otp_code")
    val otpCode: Any?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("user_name")
    val userName: String?
)