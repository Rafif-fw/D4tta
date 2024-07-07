package com.example.datasetmanagement.ui.screens

import DatasetViewModel
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.datasetmanagement.R
import com.example.datasetmanagement.utils.Screen

@Composable
fun FormAuthorScreen(viewModel: DatasetViewModel, modifier: Modifier = Modifier) {
    val currentDataset by viewModel.currentDataset.collectAsState()
    var creators by remember { mutableStateOf(currentDataset.creators) }

    Column(
        modifier = modifier
            .background(Color(0xFFEDF6EE))
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("Dataset Donation Form", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF151101))
        Text(
            "We offer users the option to upload their dataset data to our repository. Users can provide tabular or non-tabular dataset data which will be made publicly available on our repository. Donors are free to edit their donated datasets, but edits must be approved before finalizing.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF151101)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = currentDataset.verificator,
            onValueChange = { viewModel.updateCurrentDataset(verificator = it) },
            label = { Text("Add Verificator*", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )
        creators.forEachIndexed { index, creator ->
            OutlinedTextField(
                value = creator,
                onValueChange = {
                    creators = creators.toMutableList().apply { set(index, it) }
                    viewModel.updateCurrentDataset(creators = creators)
                },
                label = { Text("Add Creator*", color = Color(0xFF151101)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEDF6EE))
            )
        }

        Button(
            onClick = {
                if (creators.size < 4) {
                    creators = creators + ""
                    viewModel.updateCurrentDataset(creators = creators)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add creator",
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = { viewModel.navigateToScreen(Screen.FormBasic) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    viewModel.updateCurrentDataset(creators = creators)
                    viewModel.navigateToScreen(Screen.FormDataset)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Next")
            }
        }
    }
}
