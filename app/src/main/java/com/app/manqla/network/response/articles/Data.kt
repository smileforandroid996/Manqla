package com.app.manqla.network.response.articles


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("author_name")
    val authorName: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("is_like")
    val isLike: Boolean?,
    @SerializedName("is_share")
    val isShare: Boolean?,
    @SerializedName("like_count")
    val likeCount: Int?,
    @SerializedName("order")
    val order: Any?,
    @SerializedName("share_count")
    val shareCount: Int?,
    @SerializedName("title")
    val title: String?
)