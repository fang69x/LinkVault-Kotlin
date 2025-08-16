package com.fang.linkvault.domain.usecase.bookmark

import com.fang.linkvault.domain.repository.BookmarkRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository
){
    suspend operator fun invoke(id:String)= repository.deleteBookmark(id)
}