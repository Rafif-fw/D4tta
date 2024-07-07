package com.example.datasetmanagement.ui.screens

import DatasetViewModel
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.datasetmanagement.utils.Screen
import com.example.datasetmanagement.utils.saveBitmapAndGetUri

@Composable
fun FormAdditionalScreen(viewModel: DatasetViewModel, modifier: Modifier = Modifier) {
    val currentDataset by viewModel.currentDataset.collectAsState()
    var keywords by remember { mutableStateOf(currentDataset.keywords.joinToString(", ")) }
    var additionalInfo by remember { mutableStateOf(currentDataset.additionalInfo) }
    var datasetPhotoUri by remember { mutableStateOf<Uri?>(currentDataset.datasetPhoto?.let { Uri.parse(it) }) }
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                datasetPhotoUri = uri
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                val uri = saveBitmapAndGetUri(context, bitmap)
                datasetPhotoUri = uri
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
            "We offer users the option to upload their dataset data to our repository. Users can provide tabular or non-tabular dataset data which will be made publicly available on our repository. Donors are free to edit their donated datasets, but edits must be approved before finalizing.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF151101)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = keywords,
            onValueChange = { keywords = it },
            label = { Text("Keywords", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = additionalInfo,
            onValueChange = { additionalInfo = it },
            label = { Text("Additional Information", color = Color(0xFF151101)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEDF6EE))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3F3F3))
                .padding(16.dp)
                .clickable { showDialog = true },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Add Dataset Photo Review", color = Color(0xFF151101))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Choose a file or drag and drop here", color = Color(0xFF151101))
        }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Add Dataset Photo Review", style = MaterialTheme.typography.headlineSmall, color = Color(0xFF151101))
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                showDialog = false
                                imagePickerLauncher.launch("image/*")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
                        ) {
                            Text("Upload an Image")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                showDialog = false
                                cameraLauncher.launch(null)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
                        ) {
                            Text("Capture an Image")
                        }
                    }
                }
            }
        }

        datasetPhotoUri?.let {
            val bitmap = loadBitmap(context, it)
            bitmap?.let { bmp ->
                Image(bitmap = bmp.asImageBitmap(), contentDescription = null, modifier = Modifier.size(128.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = { viewModel.navigateToScreen(Screen.FormDataset) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    val updatedDataset = viewModel.currentDataset.value.copy(
                        keywords = keywords.split(", ").toList(),
                        additionalInfo = additionalInfo,
                        datasetPhoto = datasetPhotoUri?.toString() ?: "" // Convert Uri to String
                    )
                    viewModel.updateCurrentDataset(updatedDataset)
                    viewModel.navigateToScreen(Screen.ConfirmationForm)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Next")
            }
        }
    }
}

fun loadBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT < 28) {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
