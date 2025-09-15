package com.fang.linkvault.presentation.ui.home

sealed class HomeUiEvent {
    data object NavigateToCreateBookmark : HomeUiEvent()
    data class NavigateToBookmarkDetail(val bookmarkId :String): HomeUiEvent()
    data class ShowSnackbar(val message :String): HomeUiEvent()
}