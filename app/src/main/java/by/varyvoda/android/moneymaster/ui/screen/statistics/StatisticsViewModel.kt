package by.varyvoda.android.moneymaster.ui.screen.statistics

import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.DateRangeSuggestion
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange
import by.varyvoda.android.moneymaster.data.service.statistics.StatisticsService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable

@Serializable
object StatisticsDestination

class StatisticsViewModel(
    private val statisticsService: StatisticsService,
) : BaseViewModel<StatisticsDestination>() {

    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState = _uiState.asStateFlow()

    val dateRangeSuggestions = MutableStateFlow(DateRangeSuggestion.default()).asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val statistics = _uiState
        .flatMapLatest {
            with(it) {
                if (dateRange == null) return@flatMapLatest emptyFlow()
                statisticsService.getCategoriesStatistics(
                    operationType = operationType,
                    dateRange = dateRange,
                )
            }
        }
        .stateInThis(null)

    fun changeOperationType(operationType: Operation.Type) {
        _uiState.update { it.copy(operationType = operationType) }
    }

    fun changeDateRange(dateRange: PrimitiveDateRange?) {
        _uiState.update { it.copy(dateRange = dateRange) }
    }
}

data class StatisticsUiState(
    val operationType: Operation.Type = Operation.Type.DEFAULT,
    val dateRange: PrimitiveDateRange? = null,
)