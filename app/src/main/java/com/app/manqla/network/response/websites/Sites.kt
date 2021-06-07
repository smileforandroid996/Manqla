package com.app.manqla.network.response.websites


import com.google.gson.annotations.SerializedName

data class Sites(
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("last_page")
    val lastPage: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)