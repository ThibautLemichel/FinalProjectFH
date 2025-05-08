package com.example.finalprojectfh


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectfh.ui.theme.FinalProjectFHTheme
import androidx.activity.ComponentActivity
import androidx.navigation.navArgument
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalProjectFHTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, viewModel: NoteViewModel, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "main_screen",
        modifier = modifier
    ) {
        composable("main_screen") {
            NoteScreen(
                viewModel = viewModel,
                onAddNoteClick = { navController.navigate("add_note_screen") },
                onEditNoteClick = { note ->
                    navController.navigate("add_note_screen?noteId=${note.id}")
                }
            )
        }
        composable(
            "add_note_screen?noteId={noteId}",
            arguments = listOf(
                navArgument("noteId") { nullable = true }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            AddNoteScreen(
                viewModel = viewModel,
                noteId = noteId,
                onSaveComplete = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun AddNoteScreen(viewModel: NoteViewModel, noteId: Int?, onSaveComplete: () -> Unit) {
    val note = noteId?.let { id ->
        viewModel.allNotes.collectAsState(initial = emptyList()).value.find { it.id == id }
    }
    var noteContent by remember { mutableStateOf(note?.content ?: "") }

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            TextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Enter your note") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (noteContent.isNotEmpty()) {
                        val timestamp = java.text.SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss",
                            java.util.Locale.getDefault()
                        ).format(java.util.Date())
                        if (note != null) {
                            viewModel.update(note.copy(content = noteContent, timestamp = timestamp))
                        } else {
                            viewModel.insert(Note(content = noteContent, timestamp = timestamp))
                        }
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