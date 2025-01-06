package by.varyvoda.android.moneymaster.data.service.statistics

import by.varyvoda.android.moneymaster.data.details.statistics.Statistics
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange
import kotlinx.coroutines.flow.Flow

interface StatisticsService {

    fun getCategoriesStatistics(
        operationType: Operation.Type,
        dateRange: PrimitiveDateRange
    ): Flow<Statistics>
}