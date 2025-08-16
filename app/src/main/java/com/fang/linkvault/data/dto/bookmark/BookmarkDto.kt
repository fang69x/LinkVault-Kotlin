package com.fang.linkvault.data.dto.bookmark

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BookmarkDto (
    @SerializedName("_id")
    val id:String,
    val title:String,
    val url:String,
    val note:String?,
    val category:String,
    val tags:List<String>?,
    @SerializedName("user")
    val userId:String,
    val createdAt:Date?,
    val updatedAt:Date?
)