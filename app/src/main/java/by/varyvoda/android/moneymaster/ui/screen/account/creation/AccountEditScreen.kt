package by.varyvoda.android.moneymaster.ui.screen.account.creation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.ui.component.AccountCard
import by.varyvoda.android.moneymaster.ui.component.AppButton
import by.varyvoda.android.moneymaster.ui.component.AppIcon
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.AppTitle
import by.varyvoda.android.moneymaster.ui.component.AppearanceBuilder
import by.varyvoda.android.moneymaster.ui.component.CurrencyListPickerDialog
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.TitledContent
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

object AccountEditDestination : NavigationDestination {
    override val route = "account/edit" // TODO arguments to edit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountCreationScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountEditViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.account_create_title)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBackClick() }) {
                        AppIcon(
                            iconRef = IconRef.Back,
                        )
                    }
                },
            )
        },
        modifier = modifier
    ) { innerPadding ->
        AccountCreationBody(
            modifier = Modifier
                .padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
fun AccountCreationBody(
    modifier: Modifier = Modifier,
    viewModel: AccountEditViewModel = viewModel()
) {
    val currencies = viewModel.currencies.collectAsState().value
    val icons = viewModel.icons.collectAsState().value
    val colorThemes = viewModel.colorThemes.collectAsState().value
    val (name, currency, initialBalance, icon, theme, iconSearchString) = viewModel.uiState.collectAsState().value
    var currencySelectionShown by remember { mutableStateOf(false) }

    FormBox(
        buttons = listOf {
            AppButton(
                onClick = { viewModel.onSaveClick() },
                enabled = viewModel.canSave(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .formPadding(),
            verticalArrangement = Arrangement.formSpacedBy()
        ) {
            AccountCard(
                IconRef.Home,
                theme = theme,
                title = name,
                balance = initialBalance,
                currency = currency,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        TitledContent(title = { AppTitle(textId = R.string.general_information) }) {
            AppTextField(
                value = name,
                label = { Text(text = stringResource(R.string.account_creation_name_field_label)) },
                onValueChange = { viewModel.changeName(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            AppTextField(
                value = initialBalance,
                onValueChange = { viewModel.changeInitialBalance(it) },
                label = { Text(text = stringResource(R.string.account_creation_balance_field_label)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions { },
                modifier = Modifier
                    .fillMaxWidth()
            )
            AppTextField(
                value = currency?.name ?: "",
                onValueChange = {},
                asButton = true,
                label = {
                    Text(
                        text = stringResource(
                            if (currency == null)
                                R.string.select_currency
                            else
                                R.string.currency
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { currencySelectionShown = true }),
            )
            if (currencySelectionShown) {
                CurrencyListPickerDialog(
                    currencies = currencies,
                    isSelected = { it.code == currency?.code },
                    onSelect = {
                        currencySelectionShown = false
                        viewModel.changeCurrency(it)
                    },
                    onDismissRequest = { currencySelectionShown = false },
                )
            }
        }

        AppearanceBuilder(
            icons = icons,
            themes = colorThemes,
            currentIconRef = icon,
            currentTheme = theme,
            onIconRefSelect = { viewModel.onSelectIcon(it) },
            onThemeSelect = { viewModel.onSelectColorTheme(it) },
            iconSearchString = iconSearchString,
            onIconSearchStringChange = { viewModel.changeIconSearchString(it) }
        )
    }
}

