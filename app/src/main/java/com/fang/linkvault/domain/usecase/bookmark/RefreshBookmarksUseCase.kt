package com.fang.linkvault.domain.usecase.bookmark

import com.fang.linkvault.domain.repository.BookmarkRepository
import javax.inject.Inject

class RefreshBookmarksUseCase @Inject constructor(
    private val repository: BookmarkRepository

) {
    suspend operator fun invoke(page:Int, limit:Int)= repository.refreshBookmark(page,limit)
}