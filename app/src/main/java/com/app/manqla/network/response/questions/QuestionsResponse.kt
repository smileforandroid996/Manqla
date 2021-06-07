package com.app.manqla.network.response.questions


import com.google.gson.annotations.SerializedName

data class QuestionsResponse(
    @SerializedName("rows")
    val rows: List<Row>?,
    @SerializedName("success")
    val success: Boolean?
)