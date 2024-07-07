package com.example.datasetmanagement.network

import com.example.datasetmanagement.model.Dataset
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Body
import retrofit2.http.Path

interface ApiService {

    @GET("datasets/")
    suspend fun getDatasets(): List<Dataset>

    @POST("datasets/")
    suspend fun insertDataset(@Body dataset: Dataset)

    @PUT("datasets/{id}/")
    suspend fun updateDataset(@Path("id") id: Int, @Body dataset: Dataset)

    @DELETE("datasets/{id}/")
    suspend fun deleteDataset(@Path("id") id: Int)
}
