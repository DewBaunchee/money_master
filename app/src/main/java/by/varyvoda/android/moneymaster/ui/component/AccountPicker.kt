package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.ui.util.formPadding

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
            accountDetails = it,
            isSelected = isSelected(it),
            onClick = { onSelect(it) }
        )
    }
}

@Composable
fun AccountOptionButton(
    accountDetails: AccountDetails,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListPickerOption(
        item = accountDetails,
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.icon_and_label_row_padding))
        ) {
            Text(text = accountDetails.model.name)
        }
    }
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
    BottomDialog(onDismissRequest) {
        TitledContent(title = { AppTitle(R.string.account) }) {
            SearchableContent(
                searchEnabled = searchEnabled,
                searchString = searchString,
                onSearchStringChange = onSearchStringChange,
                modifier = modifier
                    .formPadding(),
            ) {
                AccountListPicker(
                    accounts = accounts,
                    isSelected = isSelected,
                    onSelect = onSelect,
                )
            }
        }
    }
}
