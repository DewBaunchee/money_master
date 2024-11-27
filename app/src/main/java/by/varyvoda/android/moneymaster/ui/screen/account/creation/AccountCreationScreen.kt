package by.varyvoda.android.moneymaster.ui.screen.account.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.ui.component.AppButton
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.ListDialog
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination

object AccountCreationDestination : NavigationDestination {
    override val route = "account/create"
    override val titleRes = R.string.account_create_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountCreationScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountCreationViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.account_create_title)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountCreationBody(
    modifier: Modifier = Modifier,
    viewModel: AccountCreationViewModel = viewModel()
) {
    val (name, currency, initialBalance, theme) = viewModel.uiState.collectAsState().value
    var currencySelectionShown by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.card_padding)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AccountCard(
            Icons.Default.Home,
            theme = theme ?: AccountTheme.Default,
            title = name,
            balance = initialBalance,
            currency = currency,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
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
                items = listOf(Currency.USD, Currency.BYN),
                current = currency,
                onSelect = {
                    currencySelectionShown = false
                    viewModel.changeCurrency(it)
                },
                onDismissRequest = { currencySelectionShown = false },
                onCreateRequest = {}
            )
        }
        Text(
            text = stringResource(R.string.appearance),
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = stringResource(R.string.setup_color_for_account))
        ThemeGallery(
            themes = AccountTheme.List,
            currentTheme = theme,
            onThemeSelect = { viewModel.onSelectTheme(it) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.weight(1f))
        AppButton(
            onClick = { viewModel.onSaveClick() },
            enabled = viewModel.canSave(),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
fun ThemeGallery(
    themes: List<AccountTheme>,
    currentTheme: AccountTheme?,
    onThemeSelect: (AccountTheme) -> Unit,
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
                                Brush.horizontalGradient(theme.gradientColors),
                                MaterialTheme.shapes.small
                            )
                            .size(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (theme == currentTheme) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = stringResource(R.string.selected_theme),
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
        Box {

        }
    }
}

@Composable
fun AccountCard(
    icon: ImageVector,
    theme: AccountTheme,
    title: String,
    balance: String,
    currency: Currency?,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = theme.gradientColors,
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                )
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.card_padding))
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small),
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(R.string.account_icon),
                            tint = Color.White,
                            modifier = Modifier
                                .background(Color(1f, 1f, 1f, 0.2f))
                                .padding(4.dp)
                                .clip(MaterialTheme.shapes.small),
                        )
                    }
                    Text(
                        text = if (title.isBlank())
                            stringResource(R.string.account_creation_default_card_title)
                        else
                            title,
                        color = Color.White
                    )
                }
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = if (balance.isBlank()) "" else stringResource(R.string.amount),
                            color = Color(0xFFF1F3F6),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = makeMoneyString(balance, currency),
                            color = Color.White,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                    if (currency != null) {
                        Text(
                            text = currency.code,
                            color = Color(0xFFF1F3F6),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

private fun makeMoneyString(money: String, currency: Currency?): String {
    return if (currency == null) money else currency.symbol + money
}
