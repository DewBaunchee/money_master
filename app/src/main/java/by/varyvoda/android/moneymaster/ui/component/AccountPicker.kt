package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.domain.toBrush

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
