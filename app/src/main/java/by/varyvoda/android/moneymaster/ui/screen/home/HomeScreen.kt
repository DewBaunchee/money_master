package by.varyvoda.android.moneymaster.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.ui.component.AccountCard
import by.varyvoda.android.moneymaster.ui.component.AppIconButton
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination
import by.varyvoda.android.moneymaster.ui.theme.ProfileIconShape
import by.varyvoda.android.moneymaster.util.makeMoneyString

object HomeDestination : NavigationDestination {
    override val route = "home"
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    val accounts = viewModel.accountDetails.collectAsState().value
    val uiState = viewModel.uiState.collectAsState().value

    if (accounts == null) return

    Scaffold { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            HomeTopBar(

            )
            CardGallery(
                accountDetails = accounts,
                totalBalance = 2000,
                totalBalanceCurrency = Currency(code = "USD", "dollar", "$"),
            )
            ActionBar(
                onAddIncome = { viewModel.onAddIncomeClick() },
                onAddExpense = { viewModel.onAddExpenseClick() },
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.homescreen_padding))
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(dimensionResource(R.dimen.homescreen_padding))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.icon_button_padding))
                .clip(ProfileIconShape),
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = stringResource(R.string.profile_icon),
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(dimensionResource(R.dimen.icon_button_padding))
            )
        }
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.profile_icon_greeting_space)))
        Column {
            Text(
                text = stringResource(R.string.home_greeting),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "Matthew"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        AppIconButton(
            onClick = {},
            imageVector = Icons.Filled.Notifications,
            contentDescription = null
        )
    }
}

@Composable
fun CardGallery(
    accountDetails: List<AccountDetails>,
    totalBalance: Money,
    totalBalanceCurrency: Currency,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.homescreen_padding))
        ) {
            Text(
                text = stringResource(R.string.total_balance),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = makeMoneyString(
                    money = totalBalance.toString(),
                    currency = totalBalanceCurrency
                ),
                style = MaterialTheme.typography.displaySmall
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            items(accountDetails) {
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.card_gallery_space)))
                AccountCard(
                    icon = Icons.Filled.ShoppingCart,
                    theme = it.theme,
                    title = it.account.name,
                    balance = it.account.currentBalance.toString(),
                    currency = it.currency,
                    modifier = Modifier
                        .width(350.dp)
                )
            }
            item(null) {
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.card_gallery_space)))
            }
        }
    }
}

@Composable
fun ActionBar(
    onAddIncome: () -> Unit,
    onAddExpense: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AppIconButton(
            onClick = onAddIncome,
            imageVector = Icons.Filled.KeyboardArrowUp,
            text = stringResource(R.string.add_income)
        )
        AppIconButton(
            onClick = onAddExpense,
            imageVector = Icons.Filled.KeyboardArrowDown,
            text = stringResource(R.string.add_expense)
        )
    }
}