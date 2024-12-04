package by.varyvoda.android.moneymaster.ui.screen.account.creation

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.repository.account.theme.ColorThemeRepository
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
    colorThemeRepository: ColorThemeRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AccountCreationUiState())
    val uiState = _uiState.asStateFlow()

    val currencies = currencyRepository.getAll().stateInThis()
    val colorThemes = colorThemeRepository.getAll().stateInThis()

    init {
        combine(colorThemes, _uiState) { colorThemes, uiState ->

            if (colorThemes.isEmpty()) {
                return@combine
            }

            val currentTheme = uiState.theme
            if (currentTheme == null || colorThemes.all { it != currentTheme }) {
                val firstTheme = colorThemes.first()
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

    fun onSelectTheme(theme: ColorTheme) {
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
        if (!canSave()) throw IllegalStateException("Cannot create account")

        viewModelScope.launch {
            with(_uiState.value) {
                accountService.createAccount(
                    name = name,
                    currencyCode = currency!!.code,
                    initialBalance = initialBalance.toLong(),
                    theme = theme!!
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
    val theme: ColorTheme? = null
)