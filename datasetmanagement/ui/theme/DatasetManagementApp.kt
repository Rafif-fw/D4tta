package com.example.datasetmanagement.ui.theme

import DatasetViewModel
import EditDatasetScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.datasetmanagement.ui.screens.ConfirmationFormScreen
import com.example.datasetmanagement.ui.screens.DatasetDetailScreen
import com.example.datasetmanagement.ui.screens.FormAdditionalScreen
import com.example.datasetmanagement.ui.screens.FormAuthorScreen
import com.example.datasetmanagement.ui.screens.FormBasicScreen
import com.example.datasetmanagement.ui.screens.FormDatasetScreen
import com.example.datasetmanagement.ui.screens.WorkspaceScreen
import com.example.datasetmanagement.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatasetManagementApp() {
    val viewModel: DatasetViewModel = viewModel()
    val currentScreen by viewModel.currentScreen.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dataset Management") })
        },
        content = { paddingValues ->
            when (currentScreen) {
                Screen.Workspace -> WorkspaceScreen(viewModel, Modifier.padding(paddingValues))
                Screen.FormBasic -> FormBasicScreen(viewModel, Modifier.padding(paddingValues))
                Screen.FormAuthor -> FormAuthorScreen(viewModel, Modifier.padding(paddingValues))
                Screen.FormDataset -> FormDatasetScreen(viewModel, Modifier.padding(paddingValues))
                Screen.FormAdditional -> FormAdditionalScreen(viewModel, Modifier.padding(paddingValues))
                Screen.ConfirmationForm -> ConfirmationFormScreen(viewModel, Modifier.padding(paddingValues))
                Screen.DatasetDetail -> DatasetDetailScreen(viewModel, Modifier.padding(paddingValues))
                Screen.EditDataset -> EditDatasetScreen(viewModel, Modifier.padding(paddingValues))
                else -> Text("Unknown Screen")
            }
        }
    )
}
