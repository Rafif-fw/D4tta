package com.example.datasetmanagement.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.datasetmanagement.repository.DatasetRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SyncWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8000/") // Ganti URL ini dengan URL server Django Anda
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val repository = DatasetRepository(applicationContext)
        val notificationService = NotificationService(applicationContext)

        return try {
            val datasets = apiService.getDatasets()
            // Clear local database and insert fresh data
            repository.clearAndInsertDatasets(datasets)
            notificationService.showNotification("Dataset Sync", "Datasets have been updated")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
