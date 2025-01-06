package by.varyvoda.android.moneymaster.data.details.statistics

import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange

typealias MoneyAmountAndRatio = Pair<MoneyAmount, Float>
typealias StatisticsItem<T> = Pair<T, MoneyAmountAndRatio>
typealias StatisticsMap<T> = Map<T, MoneyAmountAndRatio>

data class Statistics(
    val dateRange: PrimitiveDateRange,
    val total: MoneyAmount,
    val currency: Currency,
    val currencyStatistics: StatisticsMap<String>,
    val categoryStatistics: StatisticsMap<Category>,
)