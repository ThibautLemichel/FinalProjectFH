package com.example.finalprojectfh

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissValue
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(
    viewModel: NoteViewModel,
    onAddNoteClick: () -> Unit,
    onEditNoteClick: (Note) -> Unit
) {
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(notes.asReversed(), key = { it.id!! }) { note ->
                val dismissState = rememberDismissState(
                    confirmStateChange = { target ->
                        if (target == DismissValue.DismissedToStart) {
                            viewModel.delete(note)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(EndToStart),
                    dismissThresholds = { FractionalThreshold(0.3f) },
                    background = {
                        val bgColor by animateColorAsState(
                            if (dismissState.targetValue == DismissValue.Default)
                                MaterialTheme.colors.surface.copy(alpha = 0.1f)
                            else
                                MaterialTheme.colors.error
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(bgColor)
                                .padding(start = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colors.onError
                            )
                        }
                    },
                    dismissContent = {
                        NoteItem(
                            note = note,
                            onClick = onEditNoteClick
                        )
                    }
                )
            }
        }
    }
}