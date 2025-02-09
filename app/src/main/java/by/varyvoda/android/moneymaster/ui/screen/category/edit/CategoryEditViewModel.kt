package by.varyvoda.android.moneymaster.ui.screen.category.edit

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.DEFAULT_ID
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.isDefault
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.CategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.ColorThemeRepository
import by.varyvoda.android.moneymaster.data.service.category.CategoryService
import by.varyvoda.android.moneymaster.data.service.icons.IconsService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.component.DeletableViewModel
import by.varyvoda.android.moneymaster.ui.component.SavableViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class CategoryEditDestination(
    val categoryId: Id = DEFAULT_ID,
)

class CategoryEditViewModel(
    private val categoryRepository: CategoryRepository,
    private val categoryService: CategoryService,
    iconsService: IconsService,
    colorThemeRepository: ColorThemeRepository,
) : BaseViewModel<CategoryEditDestination>(), SavableViewModel, DeletableViewModel {

    private val _uiState = MutableStateFlow(CategoryEditUiState())
    val uiState = _uiState.asStateFlow()

    val isCreate = uiState.map { it.editableCategoryId.isDefault }.stateInThis(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    val icons = _uiState
        .map { it.iconSearchString }
        .distinctUntilChanged()
        .flatMapLatest { iconsService.getIconRefsBySearchString(it) }
        .stateInThis()
    val colorThemes = colorThemeRepository.getAll()
        .stateInThis()
        .apply {
            alwaysSelected(
                currentFlow = _uiState.map { it.colorTheme },
                itemEqualsCurrent = { this == it },
                selector = { onSelectColorTheme(it) },
                defaultValue = ColorTheme.DEFAULT,
            )
        }

    override suspend fun doApplyDestination(destination: CategoryEditDestination) {
        if (destination.categoryId.isDefault) {
            _uiState.value = CategoryEditUiState()
            return
        }

        categoryRepository.getById(destination.categoryId).first().run {
            _uiState.update { state ->
                state.copy(
                    editableCategoryId = id,
                    name = name,
                    operationType = operationType,
                    iconRef = iconRef,
                    colorTheme = colorTheme,
                )
            }
        }
    }

    fun changeName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun changeOperationType(operationType: Operation.Type) {
        _uiState.update { it.copy(operationType = operationType) }
    }

    fun onSelectIcon(icon: IconRef) {
        _uiState.update { it.copy(iconRef = icon) }
    }

    fun onSelectColorTheme(theme: ColorTheme) {
        _uiState.update { it.copy(colorTheme = theme) }
    }

    fun changeIconSearchString(searchString: String) {
        _uiState.update { it.copy(iconSearchString = searchString) }
    }

    fun onBackClick() {
        navigateUp()
    }

    override fun canDelete() = true

    override fun delete() {
        viewModelScope.launch {
            categoryService.deleteCategory(uiState.value.editableCategoryId)
            onBackClick()
        }
    }

    override fun canSave(): Boolean {
        return with(_uiState.value) { name.isNotBlank() }
    }

    override fun save() {
        if (!canSave()) throw IllegalStateException("Cannot save category")

        viewModelScope.launch {
            with(_uiState.value) {
                categoryService.createOrUpdateCategory(
                    id = editableCategoryId,
                    name = name,
                    operationType = operationType,
                    iconRef = iconRef,
                    colorTheme = colorTheme,
                )
            }
        }
        navigateUp()
    }
}

data class CategoryEditUiState(
    val editableCategoryId: Id = DEFAULT_ID,
    val name: String = "",
    val operationType: Operation.Type = Operation.Type.DEFAULT,
    val iconRef: IconRef = IconRef.Default,
    val colorTheme: ColorTheme = ColorTheme.DEFAULT,
    val iconSearchString: String = "",
)