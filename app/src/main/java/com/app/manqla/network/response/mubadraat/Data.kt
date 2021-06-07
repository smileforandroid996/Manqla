package com.app.manqla.network.response.mubadraat


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("category_id")
    val categoryId: Int?,
    @SerializedName("category_name")
    val categoryName: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: String?
)