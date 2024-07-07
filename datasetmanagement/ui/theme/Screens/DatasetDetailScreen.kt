package com.example.datasetmanagement.ui.screens

import DatasetViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.datasetmanagement.utils.Screen

@Composable
fun DatasetDetailScreen(viewModel: DatasetViewModel, modifier: Modifier = Modifier) {
    val currentDataset = viewModel.currentDataset.collectAsState().value

    Column(modifier = modifier.padding(16.dp)) {
        Text("Title: ${currentDataset.title}")
        Text("Subtitle: ${currentDataset.subtitle}")
        Text("Instance Count: ${currentDataset.instanceCount}")
        Text("Feature Count: ${currentDataset.featureCount}")
        Text("Verificator: ${currentDataset.verificator}")
        Text("Creators: ${currentDataset.creators.joinToString(", ")}")
        Text("Has Missing Values: ${currentDataset.hasMissingValues}")
        Text("Completeness Status: ${currentDataset.completenessStatus}")
        Text("Subject Area: ${currentDataset.subjectArea}")
        Text("Associated Task: ${currentDataset.associatedTask}")
        Text("Feature Type: ${currentDataset.featureType}")
        Text("Keywords: ${currentDataset.keywords.joinToString(", ")}")
        Text("Additional Info: ${currentDataset.additionalInfo}")

        currentDataset.profileGraphic?.let {
            val painter = rememberImagePainter(data = it)
            Image(painter = painter, contentDescription = null, modifier = Modifier.size(128.dp))
        }
        currentDataset.datasetPhoto?.let {
            val painter = rememberImagePainter(data = it)
            Image(painter = painter, contentDescription = null, modifier = Modifier.size(128.dp))
        }

        Row {
            Button(onClick = { viewModel.navigateToScreen(Screen.Workspace) }) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { viewModel.deleteDataset(currentDataset) }) {
                Text("Delete")
            }
        }
    }
}
