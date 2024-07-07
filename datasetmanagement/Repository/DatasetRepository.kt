package com.example.datasetmanagement.repository

import android.content.Context
import com.example.datasetmanagement.model.Dataset
import kotlinx.coroutines.flow.Flow

class DatasetRepository(context: Context) {

    private val database = AppDatabase.getDatabase(context)

    fun getDatasets(): Flow<List<Dataset>> {
        return database.datasetDao().getDatasets()
    }

    suspend fun insertDataset(dataset: Dataset) {
        database.datasetDao().insert(dataset)
    }

    suspend fun updateDataset(dataset: Dataset) {
        database.datasetDao().update(dataset)
    }

    suspend fun deleteDataset(dataset: Dataset) {
        database.datasetDao().delete(dataset)
    }

    // New method to clear and insert datasets
    suspend fun clearAndInsertDatasets(datasets: List<Dataset>) {
        database.datasetDao().clearAll()
        database.datasetDao().insertAll(datasets)
    }
}
