package com.yourusername.linkvault.presentation.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.fang.linkvault.domain.model.Bookmark
import com.fang.linkvault.presentation.ui.home.HomeUiEvent
import com.fang.linkvault.presentation.ui.home.HomeViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    // We will get these navigation actions from our NavHost later
    onNavigateToCreateBookmark: () -> Unit,
    onNavigateToBookmarkDetail: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Collect the state from the ViewModel in a lifecycle-aware way
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Listen for one-time events from the ViewModel
    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeUiEvent.NavigateToCreateBookmark -> {
                    onNavigateToCreateBookmark()
                }
                is HomeUiEvent.NavigateToBookmarkDetail -> {
                    onNavigateToBookmarkDetail(event.bookmarkId)
                }
                is HomeUiEvent.ShowSnackbar -> {
                    // We'll add a SnackbarHost later to show this message
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("LinkVault") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAddNewBookmarkClick() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Bookmark")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                // Show a loading indicator when isLoading is true
                CircularProgressIndicator()
            } else {
                // Show the list of bookmarks
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.bookmarks) { bookmark ->
                        BookmarkItem(
                            bookmark = bookmark,
                            onItemClick = {
                                // Use the non-nullable id for navigation
                                bookmark.id?.let { id ->
                                    viewModel.onBookmarkClick(id)
                                }
                            },
                            onDeleteClick = {
                                bookmark.id?.let { id ->
                                    viewModel.deleteBookmark(id)
                                }
                            }
                        )
                    }
                }
            }
            // You can also display state.error here if it's not null
        }
    }
}

// A simple Composable for displaying a single bookmark item
// (We can make this look much better later)
@Composable
fun BookmarkItem(
    bookmark: Bookmark,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    // For now, let's just use a simple Text component.
    // We'll build a proper Card later.
    Text(
        text = bookmark.title,
        modifier = Modifier.padding(16.dp)
    )
}