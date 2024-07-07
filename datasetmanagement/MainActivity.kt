package com.example.datasetmanagement

import DatasetViewModel
import EditDatasetScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.datasetmanagement.network.NotificationService
import com.example.datasetmanagement.ui.screens.ConfirmationFormScreen
import com.example.datasetmanagement.ui.screens.DatasetDetailScreen
import com.example.datasetmanagement.ui.screens.FormAdditionalScreen
import com.example.datasetmanagement.ui.screens.FormAuthorScreen
import com.example.datasetmanagement.ui.screens.FormBasicScreen
import com.example.datasetmanagement.ui.screens.FormDatasetScreen
import com.example.datasetmanagement.ui.screens.WorkspaceScreen
import com.example.datasetmanagement.utils.Screen
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {
    private val viewModel: DatasetViewModel by viewModels()
    private lateinit var notificationService: NotificationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationService = NotificationService(this)
        setContent {
            DatasetManagementApp(viewModel)
        }
        startServerConnection()
    }

    private fun startServerConnection() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://<YOUR_IP_ADDRESS>:5000/notify")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    // Show notification using the response
                    notificationService.showNotification("Notification", it)
                }
            }
        })
    }
}

@Composable
fun DatasetManagementApp(viewModel: DatasetViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    when (currentScreen) {
        Screen.Workspace -> WorkspaceScreen(viewModel)
        Screen.FormBasic -> FormBasicScreen(viewModel)
        Screen.FormAuthor -> FormAuthorScreen(viewModel)
        Screen.FormDataset -> FormDatasetScreen(viewModel)
        Screen.FormAdditional -> FormAdditionalScreen(viewModel)
        Screen.ConfirmationForm -> ConfirmationFormScreen(viewModel)
        Screen.DatasetDetail -> DatasetDetailScreen(viewModel)
        Screen.EditDataset -> EditDatasetScreen(viewModel)
        else -> {
            // Handle unknown screen or navigate to a default screen
        }
    }
}
