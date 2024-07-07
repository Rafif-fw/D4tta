package com.example.datasetmanagement.ui.screens

import DatasetViewModel
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.datasetmanagement.model.Dataset
import com.example.datasetmanagement.utils.Screen

@Composable
fun FormBasicScreen(viewModel: DatasetViewModel, modifier: Modifier = Modifier) {
    val currentDataset by viewModel.currentDataset.collectAsState()
    var title by remember { mutableStateOf(currentDataset.title) }
    var subtitle by remember { mutableStateOf(currentDataset.subtitle) }
    var instanceCount by remember { mutableStateOf(currentDataset.instanceCount.toString()) }
    var featureCount by remember { mutableStateOf(currentDataset.featureCount.toString()) }
    var profileGraphicUri by remember { mutableStateOf<Uri?>(currentDataset.profileGraphic?.let { Uri.parse(it) }) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            profileGraphicUri = uri
        }
    )

    Column(
        modifier = modifier
            .background(Color(0xFFEDF6EE))
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Dataset Title", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = subtitle,
            onValueChange = { subtitle = it },
            label = { Text("Subtitle", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = instanceCount,
            onValueChange = { instanceCount = it },
            label = { Text("Number of Instances (Rows) in Dataset", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = featureCount,
            onValueChange = { featureCount = it },
            label = { Text("Number of Features in Dataset", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )
        Spacer(modifier = Modifier.height(16.dp))

        profileGraphicUri?.let {
            val painter = rememberImagePainter(data = it)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
        }

        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
        ) {
            Text("Choose Profile Graphic")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(
                onClick = { viewModel.navigateToScreen(Screen.Workspace) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    val dataset = Dataset(
                        title = title,
                        subtitle = subtitle,
                        instanceCount = instanceCount.toIntOrNull() ?: 0,
                        featureCount = featureCount.toIntOrNull() ?: 0,
                        profileGraphic = profileGraphicUri?.toString()
                    )
                    viewModel.updateCurrentDataset(dataset)
                    viewModel.navigateToScreen(Screen.FormAuthor)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Next")
            }
        }
    }
}
