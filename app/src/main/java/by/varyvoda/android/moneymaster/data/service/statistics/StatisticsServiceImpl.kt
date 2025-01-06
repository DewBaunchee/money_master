package by.varyvoda.android.moneymaster.data.service.statistics

import by.varyvoda.android.moneymaster.data.details.account.operation.IncomeExpenseDetails
import by.varyvoda.android.moneymaster.data.details.statistics.MoneyAmountAndRatio
import by.varyvoda.android.moneymaster.data.details.statistics.Statistics
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange
import by.varyvoda.android.moneymaster.data.repository.account.operation.OperationRepository
import by.varyvoda.android.moneymaster.data.service.currency.exchange.CurrencyExchangeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class StatisticsServiceImpl(
    private val operationRepository: OperationRepository,
    private val exchangeService: CurrencyExchangeService,
) : StatisticsService {

    override fun getCategoriesStatistics(
        operationType: Operation.Type,
        dateRange: PrimitiveDateRange,
    ) = when (operationType) {
        Operation.Type.EXPENSE, Operation.Type.INCOME ->
            getIncomeExpenseCategoriesStatistics(
                operationType = operationType,
                dateRange = dateRange,
            )

        else -> throw IllegalArgumentException("Cannot create statistics for operation type: $operationType")
    }

    private fun getIncomeExpenseCategoriesStatistics(
        operationType: Operation.Type,
        dateRange: PrimitiveDateRange,
    ): Flow<Statistics> = operationRepository.getAllDetailsByTypeAndBetweenDateRange(
        operationType = operationType,
        dateRange = dateRange,
    ).map { operations ->
        val mainCurrency = Currency.USD
        val currencyCodesToAmount = mutableMapOf<String, MoneyAmount>()
        var total = MoneyAmount()
        val categoriesToAmount = mutableMapOf<Category, MoneyAmount>()

        for (operation in operations) {
            operation as IncomeExpenseDetails
            val category = operation.category.first()
            val currencyCode = operation.account.first().model.currencyCode
            val amountInMainCurrency = exchangeService.exchangeAmount(
                amount = operation.model.amount,
                currencyCode = currencyCode,
                targetCurrencyCode = mainCurrency.code,
            ).first()

            total += amountInMainCurrency
            currencyCodesToAmount.compute(
                currencyCode,
            ) { _, amount -> (amount ?: MoneyAmount()) + amountInMainCurrency }
            categoriesToAmount.compute(
                category,
            ) { _, amount -> (amount ?: MoneyAmount()) + amountInMainCurrency }
        }

        return@map Statistics(
            dateRange = dateRange,
            total = total,
            currency = mainCurrency,
            currencyStatistics = currencyCodesToAmount.toStatisticsList(total),
            categoryStatistics = categoriesToAmount.toStatisticsList(total),
        )
    }

    private fun <T> Map<T, MoneyAmount>.toStatisticsList(totalAmount: MoneyAmount) =
        mapValues {
            MoneyAmountAndRatio(
                it.value,
                (it.value.doubleValue / totalAmount.doubleValue).toFloat()
            )
        }.toList().sortedByDescending { it.second.ratio }
}