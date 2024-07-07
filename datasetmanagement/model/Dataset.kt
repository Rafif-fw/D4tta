package com.example.datasetmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.datasetmanagement.utils.Converters

@Entity(tableName = "dataset_table")
@TypeConverters(Converters::class)
data class Dataset(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var subtitle: String = "",
    var instanceCount: Int = 0,
    var featureCount: Int = 0,
    var profileGraphic: String? = null,
    var verificator: String = "",
    var creators: List<String> = listOf(),
    var hasMissingValues: Boolean = false,
    var completenessStatus: String = "",
    var subjectArea: String = "",
    var associatedTask: String = "",
    var featureType: String = "",
    var keywords: List<String> = listOf(),
    var additionalInfo: String = "",
    var datasetPhoto: String? = null
)
