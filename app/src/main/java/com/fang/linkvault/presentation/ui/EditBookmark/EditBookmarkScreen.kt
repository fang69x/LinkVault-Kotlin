package com.fang.linkvault.presentation.ui.EditBookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.util.TableInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookmarkScreen(
    onNavigateBack:()->Unit,
    viewModel: EditBookmarkViewModel= hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isBookmarkSaved) {
        if(state.isBookmarkSaved){
            onNavigateBack()
        }
    }
    Scaffold (
        topBar = {
            TopAppBar(title = { Text("Add new Bookmark") })
        }

    ){  paddingValues ->
        Column(modifier=Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value=state.title,
                onValueChange = viewModel::onTitleChanged,
                label = {Text("title")},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                    value = state.url,
            onValueChange = viewModel::onUrlChanged,
            label={Text("url")},
            modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.category,
                onValueChange = viewModel::onCategoryChanged,
                label={Text("category")},
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.note?:"",
                onValueChange = viewModel::onNoteChanged,
                label={Text("note")},
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = viewModel::saveBookmark,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ){
                if(state.isLoading)
                {
                    CircularProgressIndicator(modifier=Modifier.size(11.dp))
                }else{
                    Text("save bookmark")
                }
            }
            if(state.error!=null){
                Text(text = state.error!!, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}