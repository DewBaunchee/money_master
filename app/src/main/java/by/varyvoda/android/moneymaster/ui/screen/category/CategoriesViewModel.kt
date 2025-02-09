package by.varyvoda.android.moneymaster.ui.screen.category

import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.CategoryRepository
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.screen.category.edit.CategoryEditDestination
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable

@Serializable
object CategoriesDestination

class CategoriesViewModel(
    categoryRepository: CategoryRepository,
) : BaseViewModel<CategoriesDestination>() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState = _uiState.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val categories = _uiState
        .flatMapLatest {
            categoryRepository.getAll(
                searchString = it.categorySearchString,
                operationType = it.operationType,
            )
        }
        .stateInThis()

    fun changeCategorySearchString(searchString: String) {
        _uiState.update { it.copy(categorySearchString = searchString) }
    }

    fun selectOperationType(operationType: Operation.Type) {
        _uiState.update { it.copy(operationType = operationType) }
    }

    fun onBackClick() {
        navigateUp()
    }

    fun onAddCategoryClick() {
        navigateTo(CategoryEditDestination())
    }

    fun onCategoryClick(categoryId: Id) {
        navigateTo(CategoryEditDestination(categoryId = categoryId))
    }
}

data class CategoriesUiState(
    val categorySearchString: String = "",
    val operationType: Operation.Type = Operation.Type.EXPENSE,
)