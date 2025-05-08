package com.example.finalprojectfh

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

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
