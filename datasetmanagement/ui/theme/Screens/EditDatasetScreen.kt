import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.datasetmanagement.ui.screens.loadBitmap
import com.example.datasetmanagement.utils.Screen
import com.example.datasetmanagement.utils.saveBitmapAndGetUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDatasetScreen(viewModel: DatasetViewModel, modifier: Modifier = Modifier) {
    val currentDataset by viewModel.currentDataset.collectAsState()
    var title by remember { mutableStateOf(currentDataset.title) }
    var subtitle by remember { mutableStateOf(currentDataset.subtitle) }
    var instanceCount by remember { mutableStateOf(currentDataset.instanceCount.toString()) }
    var featureCount by remember { mutableStateOf(currentDataset.featureCount.toString()) }
    var profileGraphicUri by remember { mutableStateOf<Uri?>(currentDataset.profileGraphic?.let { Uri.parse(it) }) }
    var verificator by remember { mutableStateOf(currentDataset.verificator) }
    var creator1 by remember { mutableStateOf(currentDataset.creators.getOrNull(0) ?: "") }
    var creator2 by remember { mutableStateOf(currentDataset.creators.getOrNull(1) ?: "") }
    var creator3 by remember { mutableStateOf(currentDataset.creators.getOrNull(2) ?: "") }
    var keyword1 by remember { mutableStateOf(currentDataset.keywords.getOrNull(0) ?: "") }
    var keyword2 by remember { mutableStateOf(currentDataset.keywords.getOrNull(1) ?: "") }
    var keyword3 by remember { mutableStateOf(currentDataset.keywords.getOrNull(2) ?: "") }
    var hasMissingValues by remember { mutableStateOf(currentDataset.hasMissingValues.toString()) }
    var completenessStatus by remember { mutableStateOf(currentDataset.completenessStatus) }
    var subjectArea by remember { mutableStateOf(currentDataset.subjectArea) }
    var associatedTask by remember { mutableStateOf(currentDataset.associatedTask) }
    var featureType by remember { mutableStateOf(currentDataset.featureType) }
    var additionalInfo by remember { mutableStateOf(currentDataset.additionalInfo) }
    var datasetPhotoUri by remember { mutableStateOf<Uri?>(currentDataset.datasetPhoto?.let { Uri.parse(it) }) }
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                profileGraphicUri = uri
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                val uri = saveBitmapAndGetUri(context, bitmap)
                profileGraphicUri = uri
            }
        }
    )

    Column(
        modifier = modifier
            .background(Color(0xFFEDF6EE))
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text("Edit Dataset", style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFF151101)))
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = subtitle,
            onValueChange = { subtitle = it },
            label = { Text("Subtitle", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = instanceCount,
            onValueChange = { instanceCount = it },
            label = { Text("Number of Instances", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = featureCount,
            onValueChange = { featureCount = it },
            label = { Text("Number of Features", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = verificator,
            onValueChange = { verificator = it },
            label = { Text("Verificator", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = creator1,
            onValueChange = { creator1 = it },
            label = { Text("Creator 1", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = creator2,
            onValueChange = { creator2 = it },
            label = { Text("Creator 2", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = creator3,
            onValueChange = { creator3 = it },
            label = { Text("Creator 3", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = keyword1,
            onValueChange = { keyword1 = it },
            label = { Text("Keyword 1", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = keyword2,
            onValueChange = { keyword2 = it },
            label = { Text("Keyword 2", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = keyword3,
            onValueChange = { keyword3 = it },
            label = { Text("Keyword 3", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = hasMissingValues,
            onValueChange = { hasMissingValues = it },
            label = { Text("Has Missing Values", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = completenessStatus,
            onValueChange = { completenessStatus = it },
            label = { Text("Completeness Status", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = subjectArea,
            onValueChange = { subjectArea = it },
            label = { Text("Subject Area", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = associatedTask,
            onValueChange = { associatedTask = it },
            label = { Text("Associated Task", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = featureType,
            onValueChange = { featureType = it },
            label = { Text("Feature Type", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = additionalInfo,
            onValueChange = { additionalInfo = it },
            label = { Text("Additional Information", color = Color(0xFF151101)) },
            modifier = Modifier.fillMaxWidth()
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
                onClick = { viewModel.navigateToScreen(Screen.Workspace) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    val updatedDataset = currentDataset.copy(
                        title = title,
                        subtitle = subtitle,
                        instanceCount = instanceCount.toIntOrNull() ?: 0,
                        featureCount = featureCount.toIntOrNull() ?: 0,
                        profileGraphic = profileGraphicUri?.toString(),
                        verificator = verificator,
                        creators = listOf(creator1, creator2, creator3),
                        keywords = listOf(keyword1, keyword2, keyword3),
                        hasMissingValues = hasMissingValues.toBoolean(),
                        completenessStatus = completenessStatus,
                        subjectArea = subjectArea,
                        associatedTask = associatedTask,
                        featureType = featureType,
                        additionalInfo = additionalInfo,
                        datasetPhoto = datasetPhotoUri?.toString() ?: ""
                    )
                    viewModel.updateCurrentDataset(updatedDataset)
                    viewModel.saveCurrentDataset()
                    viewModel.navigateToScreen(Screen.Workspace)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28412E))
            ) {
                Text("Save")
            }
        }
    }
}
