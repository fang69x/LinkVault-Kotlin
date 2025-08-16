package com.fang.linkvault.domain.usecase.bookmark

import com.fang.linkvault.domain.model.Bookmark
import com.fang.linkvault.domain.repository.BookmarkRepository
import javax.inject.Inject

class CreateBookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository
){
 suspend operator fun invoke(bookmark: Bookmark)= repository.createBookmark(bookmark)
}