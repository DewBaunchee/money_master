package by.varyvoda.android.moneymaster.ui.screen.account.creation

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.repository.account.theme.AccountThemeRepository
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.all
import kotlin.collections.first

class AccountEditViewModel(
    private val accountService: AccountService,
    currencyRepository: CurrencyRepository,
    accountThemeRepository: AccountThemeRepository
) : BaseViewModel() {

    val currencies = currencyRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(DEFAULT_TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    val accountThemes = accountThemeRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(DEFAULT_TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    private val _uiState = MutableStateFlow(AccountCreationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        combine(accountThemes, _uiState) { accountThemes, uiState ->

            if (accountThemes.isEmpty()) {
                return@combine
            }

            val currentThemeId: Id? = uiState.theme?.id
            if (currentThemeId == null || accountThemes.all { it.id != currentThemeId }) {
                val firstTheme = accountThemes.first()
                onSelectTheme(firstTheme)
            }

        }.launchIn(scope = viewModelScope)
    }

    fun changeName(name: String) {
        this._uiState.update { it.copy(name = name) }
    }

    fun changeCurrency(currency: Currency) {
        this._uiState.update { it.copy(currency = currency) }
    }

    fun changeInitialBalance(initialBalance: String) {
        this._uiState.update { it.copy(initialBalance = initialBalance) }
    }

    fun onSelectTheme(theme: AccountTheme) {
        this._uiState.update { it.copy(theme = theme) }
    }

    fun canSave(): Boolean {
        return with(uiState.value) {
            name.isNotBlank()
                    && currency != null
                    && theme != null
        }
    }

    fun onSaveClick() {
        if (!canSave())
            throw IllegalStateException("Cannot create account")

        uiState.value.let { (name, currency, initialBalance, theme) ->
            viewModelScope.launch {
                accountService.createAccount(
                    name = name,
                    currencyCode = currency!!.code,
                    initialBalance = initialBalance.toLong(),
                    themeId = theme!!.id
                )
            }
        }
        navigateUp()
    }

    fun onBackClick() {
        navigateUp()
    }
}

data class AccountCreationUiState(
    val name: String = "",
    val currency: Currency? = null,
    val initialBalance: String = "",
    val theme: AccountTheme? = null
)