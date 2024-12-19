package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.domain.toBrush
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun AccountListPicker(
    accounts: List<AccountDetails>,
    isSelected: (AccountDetails) -> Boolean,
    onSelect: (AccountDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListPicker(
        items = accounts,
        modifier = modifier,
    ) {
        AccountOptionButton(
            account = it,
            isSelected = isSelected(it),
            onClick = { onSelect(it) }
        )
    }
}

@Composable
fun AccountOptionButton(
    account: AccountDetails,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListPickerOption(
        item = account,
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
    ) {
        AccountIconAndName(it)
    }
}

@Composable
fun AccountIcon(
    account: AccountDetails?,
    modifier: Modifier = Modifier,
) {
    SquareBox(
        background = account?.run { model.theme.colors.toBrush() },
        iconRef = account?.run { model.iconRef },
        tint = MaterialTheme.colorScheme.secondary,
        modifier = modifier,
    )
}

@Composable
fun AccountIconAndName(
    account: AccountDetails,
    modifier: Modifier = Modifier,
) {
    IconAndText(
        icon = { AccountIcon(account = account) },
        text = { Text(text = account.model.name) },
        modifier = modifier,
    )
}

@Composable
fun AccountListPickerDialog(
    accounts: List<AccountDetails>,
    isSelected: (AccountDetails) -> Boolean,
    onSelect: (AccountDetails) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    searchEnabled: Boolean = false,
    searchString: String = "",
    onSearchStringChange: (String) -> Unit = {},
) {
    TitledSearchableBottomDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        searchEnabled = searchEnabled,
        searchString = searchString,
        onSearchStringChange = onSearchStringChange,
    ) {
        AccountListPicker(
            accounts = accounts,
            isSelected = isSelected,
            onSelect = onSelect,
        )
    }
}

@Composable
fun AccountAndAmount(
    accounts: List<AccountDetails>,
    account: AccountDetails?,
    onSelectAccount: (AccountDetails) -> Unit,
    amount: String,
    onAmountChange: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.formSpacedBy(),
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        AccountSelect(
            accounts = accounts,
            selectedAccount = account,
            onSelectAccount = onSelectAccount,
            modifier = Modifier
                .weight(1f)
        )
        BalanceField(
            value = amount,
            onValueChange = onAmountChange,
            labelId = R.string.amount,
            imeAction = ImeAction.Done,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun AccountSelect(
    accounts: List<AccountDetails>,
    selectedAccount: AccountDetails?,
    onSelectAccount: (AccountDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    var accountSelectionShown by remember { mutableStateOf(false) }
    AppTextField(
        value = selectedAccount?.model?.name ?: "",
        onValueChange = {},
        asButton = true,
        label = {
            Text(
                text = stringResource(
                    if (selectedAccount == null)
                        R.string.select_account
                    else
                        R.string.account
                )
            )
        },
        modifier = modifier
            .clickable(onClick = { accountSelectionShown = true }),
    )
    if (accountSelectionShown) {
        AccountListPickerDialog(
            accounts = accounts,
            isSelected = { it.id == selectedAccount?.id },
            onSelect = {
                accountSelectionShown = false
                onSelectAccount(it)
            },
            onDismissRequest = { accountSelectionShown = false }
        ) {
        }
    }
}
