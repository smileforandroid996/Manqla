package com.app.manqla.network.response.article


import com.google.gson.annotations.SerializedName

data class Blog(
    @SerializedName("author_name")
    val authorName: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("is_like")
    val isLike: Boolean?,
    @SerializedName("like_count")
    val likeCount: Int?,
    @SerializedName("share_link")
    val shareLink: String?,
    @SerializedName("title")
    val title: String?
)