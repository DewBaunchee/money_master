package by.varyvoda.android.moneymaster.ui.screen.account.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.ui.component.AccountCard
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.AppTitle
import by.varyvoda.android.moneymaster.ui.component.AppearanceBuilder
import by.varyvoda.android.moneymaster.ui.component.CurrencySelect
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.MainTopBar
import by.varyvoda.android.moneymaster.ui.component.SaveButton
import by.varyvoda.android.moneymaster.ui.component.TitledContent
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun AccountEditScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountEditViewModel
) {
    Column(
        modifier = modifier,
    ) {
        MainTopBar(
            titleId = R.string.create_account,
            onBackClick = { viewModel.onBackClick() }
        )
        AccountEditBody(viewModel = viewModel)
    }
}

@Composable
fun AccountEditBody(
    viewModel: AccountEditViewModel,
    modifier: Modifier = Modifier,
) {
    val currencies = viewModel.currencies.collectAsState().value
    val icons = viewModel.icons.collectAsState().value
    val colorThemes = viewModel.colorThemes.collectAsState().value
    val (name, currency, initialBalance, iconRef, theme, iconSearchString) = viewModel.uiState.collectAsState().value

    FormBox(
        buttons = listOf {
            SaveButton(viewModel = viewModel)
        },
        modifier = modifier,
    ) { buttonColumnHeightDp ->
        Column(
            verticalArrangement = Arrangement.formSpacedBy(),
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            AccountCard(
                iconRef = iconRef,
                theme = theme,
                title = name,
                balance = initialBalance,
                currency = currency,
                modifier = Modifier
                    .fillMaxWidth()
                    .formPadding()
            )
            TitledContent(
                applyFormPadding = false,
                title = { AppTitle(textId = R.string.general_information) },
                modifier = Modifier.padding(horizontal = formPadding())
            ) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                )
                CurrencySelect(
                    currencies = currencies,
                    selectedCurrency = currency,
                    onSelect = { viewModel.changeCurrency(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            AppearanceBuilder(
                icons = icons,
                themes = colorThemes,
                currentIconRef = iconRef,
                currentTheme = theme,
                onIconRefSelect = { viewModel.onSelectIcon(it) },
                onThemeSelect = { viewModel.onSelectColorTheme(it) },
                iconSearchString = iconSearchString,
                onIconSearchStringChange = { viewModel.changeIconSearchString(it) },
                modifier = modifier
                    .formPadding(),
            )
            Spacer(Modifier.height(buttonColumnHeightDp))
        }
    }
}

