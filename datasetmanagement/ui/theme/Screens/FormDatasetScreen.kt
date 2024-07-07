package com.example.datasetmanagement.ui.screens

import DatasetViewModel
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.datasetmanagement.utils.Screen

@Composable
fun FormDatasetScreen(viewModel: DatasetViewModel, modifier: Modifier = Modifier) {
    val currentDataset by viewModel.currentDataset.collectAsState()
    var hasMissingValues by remember { mutableStateOf(currentDataset.hasMissingValues) }
    var completenessStatus by remember { mutableStateOf(currentDataset.completenessStatus) }
    var subjectArea by remember { mutableStateOf(currentDataset.subjectArea) }
    var associatedTask by remember { mutableStateOf(currentDataset.associatedTask) }
    var featureType by remember { mutableStateOf(currentDataset.featureType) }
    var datasetFileUri by remember { mutableStateOf<Uri?>(currentDataset.datasetPhoto?.let { Uri.parse(it) }) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val documentPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                datasetFileUri = uri
            }
        }
    )

    Column(
        modifier = modifier
            .background(Color(0xFFEDF6EE))
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("Dataset Donation Form", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF151101))
        Text(
            "We offer users the option to upload their dataset data to our repository. Users can provide tabular or non-tabular dataset data which will be made publicly available on our repository. Donators are free to edit their donated datasets, but edits must be approved before finalizing.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF151101)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = hasMissingValues,
                onCheckedChange = { hasMissingValues = it }
            )
            Text("Does data have missing values?", color = Color(0xFF151101))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Completeness Status", color = Color(0xFF151101))
        Box {
            Button(
                onClick = { dropdownExpanded = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text(if (completenessStatus.isEmpty()) "Select Completeness Status" else completenessStatus)
            }
            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Not Completed") },
                    onClick = { completenessStatus = "Not Completed"; dropdownExpanded = false }
                )
                DropdownMenuItem(
                    text = { Text("Almost Completed") },
                    onClick = { completenessStatus = "Almost Completed"; dropdownExpanded = false }
                )
                DropdownMenuItem(
                    text = { Text("Completed") },
                    onClick = { completenessStatus = "Completed"; dropdownExpanded = false }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = subjectArea,
            onValueChange = { subjectArea = it },
            label = { Text("Subject Area", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = associatedTask,
            onValueChange = { associatedTask = it },
            label = { Text("Associated Task", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = featureType,
            onValueChange = { featureType = it },
            label = { Text("Feature Type", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { documentPickerLauncher.launch("*/*") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
        ) {
            Text("Upload Dataset Document")
        }

        datasetFileUri?.let {
            Text("File selected: ${it.path}", color = Color(0xFF151101))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = { viewModel.navigateToScreen(Screen.FormAuthor) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    val updatedDataset = viewModel.currentDataset.value.copy(
                        hasMissingValues = hasMissingValues,
                        completenessStatus = completenessStatus,
                        subjectArea = subjectArea,
                        associatedTask = associatedTask,
                        featureType = featureType,
                        datasetPhoto = datasetFileUri?.toString() ?: "" // Convert Uri to String
                    )
                    viewModel.updateCurrentDataset(updatedDataset)
                    viewModel.navigateToScreen(Screen.FormAdditional)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Next")
            }
        }
    }
}
