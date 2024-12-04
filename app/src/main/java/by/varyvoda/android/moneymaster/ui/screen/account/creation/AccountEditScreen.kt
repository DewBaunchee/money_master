package by.varyvoda.android.moneymaster.ui.screen.account.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.toBrush
import by.varyvoda.android.moneymaster.data.model.icon.IconRef.Companion.toIconRef
import by.varyvoda.android.moneymaster.ui.component.AccountCard
import by.varyvoda.android.moneymaster.ui.component.AppButton
import by.varyvoda.android.moneymaster.ui.component.AppIcon
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.ListDialog
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination
import by.varyvoda.android.moneymaster.ui.util.formPadding

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
                            iconRef = Icons.AutoMirrored.Filled.ArrowBack.toIconRef(stringResource(R.string.back)),
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
    val colorThemes = viewModel.colorThemes.collectAsState().value
    val (name, currency, initialBalance, theme) = viewModel.uiState.collectAsState().value
    var currencySelectionShown by remember { mutableStateOf(false) }

    if (theme == null) return

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
                .padding(formPadding()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.form_spaced_by))
        ) {
            AccountCard(
                Icons.Default.Home.toIconRef(),
                theme = theme,
                title = name,
                balance = initialBalance,
                currency = currency,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.general_information),
                style = MaterialTheme.typography.titleMedium
            )
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
                ListDialog(
                    items = currencies,
                    isSelected = { it.code == currency?.code },
                    onSelect = {
                        currencySelectionShown = false
                        viewModel.changeCurrency(it)
                    },
                    onDismissRequest = { currencySelectionShown = false },
                ) {
                    Text(text = it.name)
                }
            }
            Text(
                text = stringResource(R.string.appearance),
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = stringResource(R.string.setup_color_for_account))
            ThemeGallery(
                themes = colorThemes,
                currentTheme = theme,
                onThemeSelect = { viewModel.onSelectTheme(it) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ThemeGallery(
    themes: List<ColorTheme>,
    currentTheme: ColorTheme?,
    onThemeSelect: (ColorTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.card_padding))
        ) {
            items(themes) { theme ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(onClick = { onThemeSelect(theme) })
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                theme.colors.toBrush(),
                                MaterialTheme.shapes.small
                            )
                            .size(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (theme == currentTheme) {
                            AppIcon(
                                iconRef = Icons.Filled.Done.toIconRef(stringResource(R.string.selected_theme)),
                                tint = Color.White,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = theme.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}
