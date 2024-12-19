package by.varyvoda.android.moneymaster.data.service.balance

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.service.currency.exchange.CurrencyExchangeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

class BalanceServiceImpl(
    private val currencyExchangeService: CurrencyExchangeService,
) : BalanceService {

    override fun calculateTotalBalance(
        targetCurrencyCode: String,
        accounts: List<AccountDetails>
    ): Flow<MoneyAmount> =
        combine(
            accounts.map {
                if (it.model.currencyCode == targetCurrencyCode)
                    flowOf(it.model.currentBalance)
                else
                    currencyExchangeService.exchangeAmount(
                        it.model.currentBalance,
                        it.model.currencyCode,
                        targetCurrencyCode,
                    )
            }
        ) { amounts ->
            amounts.reduce { acc, moneyAmount -> acc + moneyAmount }
        }
}