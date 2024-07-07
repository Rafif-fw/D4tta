package com.example.datasetmanagement.ui.screens

import DatasetViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.datasetmanagement.R
import com.example.datasetmanagement.model.Dataset
import com.example.datasetmanagement.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceScreen(viewModel: DatasetViewModel, modifier: Modifier = Modifier) {
    val arialce = FontFamily(Font(R.font.arialce))
    val ab = FontFamily((Font(R.font.ab)))
    val arialbi = FontFamily((Font(R.font.arialbi)))
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workspace", color = Color(0xFF151101)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFEDF6EE))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = modifier
                    .background(Color(0xFFEDF6EE))
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    "Your Work",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 30.sp, 
                        fontFamily = ab,
                        color = Color(0xFF151101)
                    )
                )
                Text(
                    "This is a private view of your content, to see what others see, visit your profile",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp,
                        fontFamily = arialce,
                        color = Color(0xFF151101)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.onCreateDatasetClicked() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E)),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp) 
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Create Dataset")
                }
                Spacer(modifier = Modifier.height(8.dp))

                val datasets by viewModel.datasets.collectAsState()
                datasets.forEach { dataset ->
                    if (dataset.title.isNotEmpty()) {
                        DatasetItem(
                            dataset,
                            onEditClick = {
                                viewModel.onDatasetSelected(dataset)
                                viewModel.navigateToScreen(Screen.EditDataset)
                            },
                            onRemoveClick = {
                                viewModel.deleteDataset(dataset)
                            },
                            fontFamily = ab 
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun DatasetItem(
    dataset: Dataset,
    onEditClick: () -> Unit,
    onRemoveClick: () -> Unit,
    fontFamily: FontFamily
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) 
            .clickable { onEditClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = rememberImagePainter(data = dataset.profileGraphic) {
            placeholder(R.drawable.placeholder)
            error(R.drawable.placeholder)
        }
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(48.dp), 
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                dataset.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontFamily = fontFamily,
                    color = Color(0xFF151101)
                )
            )
            Text(
                dataset.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp, 
                    fontFamily = fontFamily,
                    color = Color(0xFF151101)
                )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onEditClick() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E2841)),
                modifier = Modifier.width(110.dp).height(36.dp) // Atur ukuran tombol
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp) 
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text("Edit", fontSize = 12.sp) 
            }
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = { onRemoveClick() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF412E28)),
                modifier = Modifier.width(110.dp).height(36.dp) 
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp) 
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text("Remove", fontSize = 12.sp) 
            }
        }
    }
}
