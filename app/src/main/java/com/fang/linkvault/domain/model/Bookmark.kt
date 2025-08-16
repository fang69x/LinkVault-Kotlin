package com.fang.linkvault.domain.model

import java.util.Date

data class Bookmark (
    val id :String,
    val title:String,
    val url :String,
    val note:String?,
    val category:String,
    val tags:List<String>?,
    val userId:String,
    val createdAt: Date?,
    val updatedAt:Date?
)