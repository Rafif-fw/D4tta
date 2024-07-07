package com.example.datasetmanagement.ui.screens

import DatasetViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.datasetmanagement.R
import com.example.datasetmanagement.model.Dataset
import com.example.datasetmanagement.utils.Screen

@Composable
fun ConfirmationFormScreen(viewModel: DatasetViewModel, modifier: Modifier = Modifier) {
    val dataset by viewModel.currentDataset.collectAsState()

    Column(
        modifier = modifier
            .background(Color(0xFFEDF6EE))
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Enable scrolling
    ) {
        Text(
            "Dataset Management",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 30.sp,
                color = Color(0xFF2E2841),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))

        ConfirmationFormContent(dataset)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { viewModel.navigateToScreen(Screen.FormAdditional) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6c757d)),
                modifier = Modifier.weight(1f).padding(4.dp)
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    viewModel.createDataset()
                    viewModel.navigateToScreen(Screen.Workspace)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007bff)),
                modifier = Modifier.weight(1f).padding(4.dp)
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun ConfirmationFormContent(dataset: Dataset) {
    Column(modifier = Modifier.fillMaxWidth()) {
        FormSectionTitle("Title")
        FormSectionContent(dataset.title)

        FormSectionTitle("Subtitle")
        FormSectionContent(dataset.subtitle)

        FormSectionTitle("Instance Count")
        FormSectionContent(dataset.instanceCount.toString())

        FormSectionTitle("Feature Count")
        FormSectionContent(dataset.featureCount.toString())

        FormSectionTitle("Profile Graphic")
        dataset.profileGraphic?.let { profileGraphic ->
            val painter = rememberImagePainter(data = profileGraphic) {
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(144.dp)
                    .padding(8.dp)
                    .background(Color(0xFFF3F3F3))
            )
        }

        FormSectionTitle("Has Missing Values")
        FormSectionContent(dataset.hasMissingValues.toString())

        FormSectionTitle("Completeness Status")
        FormSectionContent(dataset.completenessStatus)

        FormSectionTitle("Subject Area")
        FormSectionContent(dataset.subjectArea)

        FormSectionTitle("Associated Task")
        FormSectionContent(dataset.associatedTask)

        FormSectionTitle("Feature Type")
        FormSectionContent(dataset.featureType)

        FormSectionTitle("Keywords")
        FormSectionContent(dataset.keywords.joinToString(", "))

        FormSectionTitle("Additional Info")
        FormSectionContent(dataset.additionalInfo)

        FormSectionTitle("Dataset Photo")
        dataset.datasetPhoto?.let { datasetPhoto ->
            val painter = rememberImagePainter(data = datasetPhoto) {
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(144.dp)
                    .padding(8.dp)
                    .background(Color(0xFFF3F3F3))
            )
        }
    }
}

@Composable
fun FormSectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 24.sp,
            color = Color(0xFF2E2841),
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun FormSectionContent(content: String) {
    Text(
        text = content,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 18.sp,
            color = Color(0xFF2E2841)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFE9ECEF))
            .padding(8.dp)
    )
}
