package com.fang.linkvault.domain.usecase.bookmark

import com.fang.linkvault.domain.repository.BookmarkRepository
import javax.inject.Inject

class SearchBookmarksUseCase @Inject constructor(
    private val repository: BookmarkRepository
){
    suspend operator fun invoke(query:String? ,category:String?,page:Int,limit:Int)= repository.searchBookmark(query,category,page,limit)
}