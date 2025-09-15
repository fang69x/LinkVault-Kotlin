package com.fang.linkvault.presentation.ui.home

import com.fang.linkvault.domain.model.Bookmark

data class HomeState (
    val bookmarks :List<Bookmark> = emptyList(),
    val isLoading:Boolean = false,
    val error:String ? = null
)