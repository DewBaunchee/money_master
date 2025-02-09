package by.varyvoda.android.moneymaster.ui.screen.account.edit

import androidx.lifecycle.viewModelScope
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.DEFAULT_ID
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.isDefault
import by.varyvoda.android.moneymaster.data.model.domain.toMoneyAmountOrNull
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.repository.account.theme.ColorThemeRepository
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.data.service.icons.IconsService
import by.varyvoda.android.moneymaster.ui.base.BaseViewModel
import by.varyvoda.android.moneymaster.ui.component.DeletableViewModel
import by.varyvoda.android.moneymaster.ui.component.SavableViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class AccountEditDestination(
    val accountId: Id = DEFAULT_ID,
)

class AccountEditViewModel(
    private val accountService: AccountService,
    currencyRepository: CurrencyRepository,
    iconsService: IconsService,
    colorThemeRepository: ColorThemeRepository
) : BaseViewModel<AccountEditDestination>(), SavableViewModel, DeletableViewModel {

    private val _uiState = MutableStateFlow(AccountEditUiState())
    val uiState = _uiState.asStateFlow()

    val isCreate = uiState.map { it.editableAccountId.isDefault }.stateInThis(true)

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

    override suspend fun doApplyDestination(destination: AccountEditDestination) {
        if (destination.accountId.isDefault) {
            _uiState.value = AccountEditUiState()
            return
        }

        accountService.getAccount(destination.accountId).first().run {
            _uiState.update {
                it.copy(
                    editableAccountId = model.id,
                    name = model.name,
                    currency = currency.first(),
                    // FIXME Baaaaaaaaaaaaaaaad
                    initialBalance = model.initialBalance.doubleValue.toString(),
                    iconRef = model.iconRef,
                    theme = model.theme,
                )
            }
        }
    }

    fun changeName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun changeCurrency(currency: Currency) {
        _uiState.update { it.copy(currency = currency) }
    }

    fun changeInitialBalance(initialBalance: String) {
        _uiState.update { it.copy(initialBalance = initialBalance) }
    }

    fun onSelectIcon(icon: IconRef) {
        _uiState.update { it.copy(iconRef = icon) }
    }

    fun onSelectColorTheme(theme: ColorTheme) {
        _uiState.update { it.copy(theme = theme) }
    }

    fun changeIconSearchString(searchString: String) {
        _uiState.update { it.copy(iconSearchString = searchString) }
    }

    fun onBackClick() {
        navigateUp()
    }

    override fun canDelete() = true

    override fun delete() {
        viewModelScope.launch {
            accountService.deleteAccount(uiState.value.editableAccountId)
            navigateUp()
        }
    }

    override fun canSave(): Boolean {
        return with(uiState.value) {
            name.isNotBlank()
                    && currency != null
                    && theme != ColorTheme.DEFAULT
                    && iconRef != IconRef.Default
        }
    }

    override fun save() {
        if (!canSave()) throw IllegalStateException("Cannot create account")

        viewModelScope.launch {
            with(_uiState.value) {
                accountService.createOrUpdateAccount(
                    id = editableAccountId,
                    name = name,
                    currencyCode = getCurrencyCode(),
                    initialBalance = getMoneyAmount(),
                    iconRef = iconRef,
                    theme = theme,
                )
            }
        }
        navigateUp()
    }

    private fun getCurrencyCode(): String =
        requireNotNull(uiState.value.currency) {
            "Date isn't selected"
        }.code

    private fun getMoneyAmount(): MoneyAmount =
        requireNotNull(uiState.value.initialBalance.toMoneyAmountOrNull()) {
            "Date isn't selected"
        }
}

data class AccountEditUiState(
    val editableAccountId: Id = 0,
    val name: String = "",
    val currency: Currency? = null,
    val initialBalance: String = "",
    val iconRef: IconRef = IconRef.Default,
    val theme: ColorTheme = ColorTheme.DEFAULT,
    val iconSearchString: String = "",
)