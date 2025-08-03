package com.fang.linkvault.data.dto.bookmark

import com.fang.linkvault.domain.model.Bookmark

data class SearchResponseDto(
    val bookmarks:List<BookmarkDto>,
    val page:Int,
    val totalPage:Int,
    val total:Int,
    val limit:Int
)