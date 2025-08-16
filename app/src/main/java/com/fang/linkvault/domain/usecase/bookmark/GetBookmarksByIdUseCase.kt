package com.fang.linkvault.domain.usecase.bookmark

import com.fang.linkvault.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarksByIdUseCase @Inject constructor(
    private val repository: BookmarkRepository
){
    suspend operator fun invoke(id:String) = repository.getBookmarksById(id)
}