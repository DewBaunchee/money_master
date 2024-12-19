package by.varyvoda.android.moneymaster.ui.screen.currencies

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.toMoneyAmountOrNull
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyExchangeRateRepository
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyRepository
import by.varyvoda.android.moneymaster.data.service.currency.exchange.CurrencyExchangeService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.component.SavableViewModel
import by.varyvoda.android.moneymaster.util.allNotNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object CurrenciesDestination

class CurrenciesViewModel(
    currencyRepository: CurrencyRepository,
    currencyExchangeRateRepository: CurrencyExchangeRateRepository,
    private val currencyExchangeService: CurrencyExchangeService,
) : BaseViewModel<CurrenciesDestination>(), SavableViewModel {

    private val _uiState = MutableStateFlow(CurrenciesUiState())
    val uiState = _uiState.asStateFlow()

    val currencies = currencyRepository.getAll().stateInThis(null)

    // FIXME If not found by code
    val firstCurrency get() = currencies.value?.find { it.code == uiState.value.firstCurrencyCode }
    val secondCurrency get() = currencies.value?.find { it.code == uiState.value.secondCurrencyCode }

    init {
        combine(
            _uiState.map { it.firstCurrencyCode }.filterNotNull().distinctUntilChanged(),
            _uiState.map { it.secondCurrencyCode }.filterNotNull().distinctUntilChanged(),
        ) { (firstCurrencyCode, secondCurrencyCode) ->
            combine(
                currencyExchangeRateRepository.getByCodes(
                    soldCurrencyCode = firstCurrencyCode,
                    boughtCurrencyCode = secondCurrencyCode
                ),
                currencyExchangeRateRepository.getByCodes(
                    soldCurrencyCode = secondCurrencyCode,
                    boughtCurrencyCode = firstCurrencyCode
                ),
            ) { it -> it }
        }
            .flatMapLatest { it }
            .onEach { (firstToSecondRate, secondToFirstRate) ->
                firstToSecondRate?.let { changeFirstToSecondRate(it.rate.toString()) }
                secondToFirstRate?.let { changeSecondToFirstRate(it.rate.toString()) }
            }
            .launchInThis()
    }

    fun selectFirstCurrency(code: String) {
        _uiState.update { it.copy(firstCurrencyCode = code) }
    }

    fun selectSecondCurrency(code: String) {
        _uiState.update { it.copy(secondCurrencyCode = code) }
    }

    fun changeFirstToSecondRate(rate: String) {
        _uiState.update { it.copy(firstToSecondRate = rate) }
    }

    fun changeSecondToFirstRate(rate: String) {
        _uiState.update { it.copy(secondToFirstRate = rate) }
    }

    fun onBackClick() {
        navigateUp()
    }

    override fun canSave(): Boolean {
        return with(uiState.value) {
            allNotNull(
                firstCurrencyCode,
                secondCurrencyCode,
                firstToSecondRate.toMoneyAmountOrNull(),
                secondToFirstRate.toMoneyAmountOrNull(),
            )
        }
    }

    override fun save() {
        viewModelScope.launch {
            currencyExchangeService.updateExchangeRate(
                soldCurrencyCode = getFirstCurrencyCode(),
                boughtCurrencyCode = getSecondCurrencyCode(),
                rate = getFirstToSecondRate(),
            )
            currencyExchangeService.updateExchangeRate(
                soldCurrencyCode = getSecondCurrencyCode(),
                boughtCurrencyCode = getFirstCurrencyCode(),
                rate = getSecondToFirstRate(),
            )
            navigateUp()
        }
    }

    private fun getFirstCurrencyCode(): String =
        requireNotNull(uiState.value.firstCurrencyCode) {
            "Source account isn't selected"
        }

    private fun getSecondCurrencyCode(): String =
        requireNotNull(uiState.value.secondCurrencyCode) {
            "Destination account isn't selected"
        }

    private fun getFirstToSecondRate(): MoneyAmount =
        requireNotNull(uiState.value.firstToSecondRate.toMoneyAmountOrNull()) {
            "Invalid sent amount"
        }

    private fun getSecondToFirstRate(): MoneyAmount =
        requireNotNull(uiState.value.secondToFirstRate.toMoneyAmountOrNull()) {
            "Invalid received amount"
        }
}

data class CurrenciesUiState(
    val firstCurrencyCode: String? = null,
    val secondCurrencyCode: String? = null,
    val firstToSecondRate: String = "",
    val secondToFirstRate: String = "",
)