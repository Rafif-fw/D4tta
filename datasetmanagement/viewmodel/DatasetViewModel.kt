import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.datasetmanagement.model.Dataset
import com.example.datasetmanagement.repository.DatasetRepository
import com.example.datasetmanagement.utils.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DatasetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DatasetRepository(application)

    private val _datasets = MutableStateFlow<List<Dataset>>(emptyList())
    val datasets: StateFlow<List<Dataset>> get() = _datasets

    private val _currentDataset = MutableStateFlow(Dataset())
    val currentDataset: StateFlow<Dataset> get() = _currentDataset

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Workspace)
    val currentScreen: StateFlow<Screen> get() = _currentScreen

    init {
        loadDatasets()
    }

    private fun loadDatasets() {
        viewModelScope.launch {
            repository.getDatasets().collect { datasets ->
                _datasets.value = datasets.filter { it.title.isNotBlank() }
            }
        }
    }

    fun onDatasetSelected(dataset: Dataset) {
        _currentDataset.value = dataset
        navigateToScreen(Screen.DatasetDetail)
    }

    fun onCreateDatasetClicked() {
        _currentDataset.value = Dataset()
        navigateToScreen(Screen.FormBasic)
    }

    fun updateCurrentDataset(dataset: Dataset) {
        _currentDataset.value = dataset
    }

    fun updateCurrentDataset(
        title: String = _currentDataset.value.title,
        subtitle: String = _currentDataset.value.subtitle,
        instanceCount: Int = _currentDataset.value.instanceCount,
        featureCount: Int = _currentDataset.value.featureCount,
        profileGraphic: String = _currentDataset.value.profileGraphic ?: "",
        verificator: String = _currentDataset.value.verificator,
        creators: List<String> = _currentDataset.value.creators,
        hasMissingValues: Boolean = _currentDataset.value.hasMissingValues,
        completenessStatus: String = _currentDataset.value.completenessStatus,
        subjectArea: String = _currentDataset.value.subjectArea,
        associatedTask: String = _currentDataset.value.associatedTask,
        featureType: String = _currentDataset.value.featureType,
        keywords: List<String> = _currentDataset.value.keywords,
        additionalInfo: String = _currentDataset.value.additionalInfo,
        datasetPhoto: String = _currentDataset.value.datasetPhoto ?: ""
    ) {
        _currentDataset.value = _currentDataset.value.copy(
            title = title,
            subtitle = subtitle,
            instanceCount = instanceCount,
            featureCount = featureCount,
            profileGraphic = profileGraphic,
            verificator = verificator,
            creators = creators,
            hasMissingValues = hasMissingValues,
            completenessStatus = completenessStatus,
            subjectArea = subjectArea,
            associatedTask = associatedTask,
            featureType = featureType,
            keywords = keywords,
            additionalInfo = additionalInfo,
            datasetPhoto = datasetPhoto
        )
    }

    fun navigateToScreen(screen: Screen) {
        _currentScreen.value = screen
    }

    fun createDataset() {
        viewModelScope.launch {
            repository.insertDataset(_currentDataset.value)
            loadDatasets()
        }
    }

    fun saveCurrentDataset() {
        viewModelScope.launch {
            repository.updateDataset(_currentDataset.value)
            loadDatasets()
        }
    }

    fun deleteDataset(dataset: Dataset) {
        viewModelScope.launch {
            repository.deleteDataset(dataset)
            loadDatasets()
        }
    }
}
