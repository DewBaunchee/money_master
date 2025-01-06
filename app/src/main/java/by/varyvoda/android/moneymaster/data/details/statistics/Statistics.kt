package by.varyvoda.android.moneymaster.data.details.statistics

import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange

data class MoneyAmountAndRatio(val amount: MoneyAmount, val ratio: Float)

typealias StatisticsItem<T> = Pair<T, MoneyAmountAndRatio>
typealias StatisticsList<T> = List<Pair<T, MoneyAmountAndRatio>>

data class Statistics(
    val dateRange: PrimitiveDateRange,
    val total: MoneyAmount,
    val currency: Currency,
    val currencyStatistics: StatisticsList<String>,
    val categoryStatistics: StatisticsList<Category>,
)