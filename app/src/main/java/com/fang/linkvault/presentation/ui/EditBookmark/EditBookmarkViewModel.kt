package com.fang.linkvault.presentation.ui.EditBookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fang.linkvault.domain.model.Bookmark
import com.fang.linkvault.domain.usecase.bookmark.CreateBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditBookmarkState(
    val title:String="",
    val url:String="",
    val category:String="",
    val note:String?=null,
    val isLoading :Boolean=false,
    val isBookmarkSaved: Boolean=false,
    val error :String?=null
)
@HiltViewModel
class EditBookmarkViewModel @Inject constructor(
    private val createBookmarkUseCase: CreateBookmarkUseCase
) : ViewModel(){
    private val _state= MutableStateFlow(EditBookmarkState())
    val state= _state.asStateFlow()
    fun onTitleChanged(newTitle:String){
        _state.update{it.copy(title=newTitle)}
    }
    fun onUrlChanged(newUrl:String){
        _state.update{it.copy(url=newUrl)}
    }
    fun onCategoryChanged(newCategory: String){
        _state.update{it.copy(category = newCategory)}
    }
    fun onNoteChanged(newNote:String){
        _state.update{it.copy(note=newNote)}
    }
    fun saveBookmark(){
        viewModelScope.launch{
            _state.update { it.copy(isLoading = true) }
            val bookmarkToSave= Bookmark(
                id="",
                title=_state.value.title,
                url = _state.value.url,
                category=_state.value.category,
                note=_state.value.note,
                tags=emptyList(),
                userId = "user",
                createdAt = null,
                updatedAt = null,

            )
            createBookmarkUseCase(bookmarkToSave).onSuccess { _state.update {it.copy(isLoading = false, isBookmarkSaved = true)} }.onFailure {
                _state.update { it.copy(isLoading = false, isBookmarkSaved = false) }
            } }

        }
    }