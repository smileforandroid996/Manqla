package com.app.manqla.network.response.questions


import com.google.gson.annotations.SerializedName

data class Row(
    @SerializedName("answer")
    val answer: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("question")
    val question: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)