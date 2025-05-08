package com.example.finalprojectfh

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp

@Composable
fun AddNoteScreen(viewModel: NoteViewModel, initialNote: String, onSaveComplete: () -> Unit) {
    var note by remember { mutableStateOf(initialNote) }

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            TextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Enter your note") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (note.isNotEmpty()) {
                        val timestamp = java.text.SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss",
                            java.util.Locale.getDefault()
                        ).format(java.util.Date())
                        viewModel.insert(Note(content = note, timestamp = timestamp))
                        onSaveComplete()
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Save Note")
            }
        }
    }
}