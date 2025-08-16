package com.fang.linkvault.domain.usecase.bookmark

import com.fang.linkvault.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    operator fun invoke() = repository.getBookmark()
}