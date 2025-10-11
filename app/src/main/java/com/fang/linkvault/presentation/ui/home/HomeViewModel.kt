package com.fang.linkvault.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fang.linkvault.domain.usecase.bookmark.DeleteBookmarkUseCase
import com.fang.linkvault.domain.usecase.bookmark.GetBookmarksUseCase
import com.fang.linkvault.domain.usecase.bookmark.RefreshBookmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val refreshBookmarksUseCase: RefreshBookmarksUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<HomeUiEvent>()
    val events = _eventChannel.receiveAsFlow()

    init {
        observeBookmarks()
        refreshBookmarks()
    }

    private fun observeBookmarks() {
        getBookmarksUseCase()
            .onEach { bookmarks ->
                _state.update { it.copy(bookmarks = bookmarks) }
            }
            .launchIn(viewModelScope)
    }

    fun refreshBookmarks() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = refreshBookmarksUseCase(page = 1, limit = 10)

            result.onSuccess {
                // Successfully refreshed bookmarks
                _state.update { it.copy(isLoading = false, error = null) }
            }.onFailure { error ->
                // Failed to refresh bookmarks
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            }
        }
    }

    fun deleteBookmark(id: String) {
        viewModelScope.launch {
            deleteBookmarkUseCase(id)
                .onSuccess {
                    _eventChannel.send(HomeUiEvent.ShowSnackbar("Bookmark deleted"))
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(error = error.message)
                    }
                }
        }
    }

    fun onBookmarkClick(id: String) {
        viewModelScope.launch {
            _eventChannel.send(HomeUiEvent.NavigateToBookmarkDetail(id))
        }
    }

    fun onAddNewBookmarkClick() {
        viewModelScope.launch {
            _eventChannel.send(HomeUiEvent.NavigateToCreateBookmark)
        }
    }
}