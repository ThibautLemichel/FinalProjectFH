package com.example.finalprojectfh

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteItem(
    note: Note,
    onClick: (Note) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()                              // full‐width
            .clickable { onClick(note) }
            .padding(horizontal = 16.dp, vertical = 8.dp) // nice padding
    ) {
        Text(
            text = note.content,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = note.timestamp,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), // thin, subtle
        thickness = 1.dp
    )
}
