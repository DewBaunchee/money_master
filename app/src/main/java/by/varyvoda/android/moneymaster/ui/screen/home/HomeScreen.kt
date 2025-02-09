package by.varyvoda.android.moneymaster.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.ui.component.AccountGallery
import by.varyvoda.android.moneymaster.ui.component.AppIcon
import by.varyvoda.android.moneymaster.ui.component.AppIconButton
import by.varyvoda.android.moneymaster.ui.component.IconAndText
import by.varyvoda.android.moneymaster.ui.component.MoneyText
import by.varyvoda.android.moneymaster.ui.component.TitledOperationList
import by.varyvoda.android.moneymaster.ui.theme.ProfileIconShape
import by.varyvoda.android.moneymaster.ui.util.collectValue
import by.varyvoda.android.moneymaster.ui.util.formPadding

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val (_, selectedOperationId) = viewModel.uiState.collectValue()
    val accounts = viewModel.accounts.collectValue()
    val mainCurrency = viewModel.mainCurrency.collectValue()
    val totalBalance = viewModel.totalBalance.collectValue()
    val operations = viewModel.operations.collectValue()

    if (accounts == null) return

    val currentAccount = viewModel.currentAccount

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        HomeTopBar()
        Column(
            modifier = Modifier
                .padding(horizontal = formPadding())
        ) {
            Text(
                text = stringResource(R.string.total_balance),
                style = MaterialTheme.typography.labelMedium
            )
            MoneyText(
                currency = mainCurrency,
                amount = totalBalance,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displaySmall,
            )
        }

        currentAccount?.let {
            AccountGallery(
                accounts = accounts,
                currentAccount = currentAccount,
                onSelect = { viewModel.changeCurrentAccount(it.id) }
            )
        }
        ActionBar(
            onAddIncome = { viewModel.onAddIncomeClick() },
            onAddExpense = { viewModel.onAddExpenseClick() },
            onAddTransfer = { viewModel.onAddTransferClick() },
            modifier = Modifier
                .fillMaxWidth()
        )
        currentAccount?.let {
            TitledOperationList(
                accordingToAccount = currentAccount,
                operations = operations ?: listOf(),
                onViewAllClick = {},
                isSelected = { it.id == selectedOperationId },
                onSelect = { viewModel.selectOperation(it.id) },
                onEditClick = {},
                onDeleteClick = { viewModel.onDeleteOperationClick(it.id) },
                modifier = Modifier
                    .weight(1f)
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
            .formPadding()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconAndText(
            icon = {
                Box(
                    modifier = Modifier
                        .clip(ProfileIconShape),
                ) {
                    AppIcon(
                        iconRef = IconRef.Profile,
                        modifier = Modifier
                            .background(Color.LightGray)
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = stringResource(R.string.home_greeting),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "Matthew"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        AppIconButton(
            onClick = {},
            iconRef = IconRef.Notifications,
            text = "",
        )
    }
}

@Composable
fun ActionBar(
    onAddIncome: () -> Unit,
    onAddExpense: () -> Unit,
    onAddTransfer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AppIconButton(
            onClick = onAddIncome,
            iconRef = IconRef.AddIncome,
        )
        AppIconButton(
            onClick = onAddExpense,
            iconRef = IconRef.AddExpense,
        )
        AppIconButton(
            onClick = onAddTransfer,
            iconRef = IconRef.AddTransfer,
        )
    }
}