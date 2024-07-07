package com.example.datasetmanagement.repository

import androidx.room.*
import com.example.datasetmanagement.model.Dataset
import kotlinx.coroutines.flow.Flow

@Dao
interface DatasetDao {

    @Query("SELECT * FROM dataset_table")
    fun getDatasets(): Flow<List<Dataset>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dataset: Dataset)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datasets: List<Dataset>)

    @Query("DELETE FROM dataset_table")
    suspend fun clearAll()

    @Delete
    suspend fun delete(dataset: Dataset)

    @Update
    suspend fun update(dataset: Dataset)
}
