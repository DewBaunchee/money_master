package by.varyvoda.android.moneymaster.ui.screen.account.edit

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.repository.account.theme.ColorThemeRepository
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.data.service.icons.IconsService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class AccountEditDestination(
    val accountId: Id? = null,
)

class AccountEditViewModel(
    private val accountService: AccountService,
    currencyRepository: CurrencyRepository,
    iconsService: IconsService,
    colorThemeRepository: ColorThemeRepository
) : BaseViewModel<AccountEditDestination>() {

    private val _uiState = MutableStateFlow(AccountCreationUiState())
    val uiState = _uiState.asStateFlow()

    val currencies = currencyRepository.getAll().stateInThis()

    @OptIn(ExperimentalCoroutinesApi::class)
    val icons = _uiState
        .map { it.iconSearchString }
        .distinctUntilChanged()
        .flatMapLatest { iconsService.getIconRefsBySearchString(it) }
        .stateInThis()
    val colorThemes = colorThemeRepository.getAll().stateInThis()
        .apply {
            alwaysSelected(
                currentFlow = _uiState.map { it.theme },
                itemEqualsCurrent = { this == it },
                selector = { onSelectColorTheme(it) },
                defaultValue = ColorTheme.DEFAULT,
            )
        }

    override fun applyDestination(destination: AccountEditDestination) {

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

    fun onSelectIcon(icon: IconRef) {
        this._uiState.update { it.copy(iconRef = icon) }
    }

    fun onSelectColorTheme(theme: ColorTheme) {
        this._uiState.update { it.copy(theme = theme) }
    }

    fun changeIconSearchString(searchString: String) {
        this._uiState.update { it.copy(iconSearchString = searchString) }
    }

    fun canSave(): Boolean {
        return with(uiState.value) {
            name.isNotBlank()
                    && currency != null
                    && theme != ColorTheme.DEFAULT
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
                    iconRef = iconRef,
                    theme = theme,
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
    val iconRef: IconRef = IconRef.Default,
    val theme: ColorTheme = ColorTheme.DEFAULT,
    val iconSearchString: String = "",
)