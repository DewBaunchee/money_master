package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.account.Account

@Composable
fun AccountSelect(
    accounts: List<Account>,
    current: Account?,
    onSelect: (Account) -> Unit,
    onCreateRequest: (() -> Unit),
    modifier: Modifier = Modifier
) {
    var chooseShown by remember { mutableStateOf(false) }

    if (chooseShown) {
        SelectDialog(
            items = accounts,
            current = current,
            onSelect = { chooseShown = false; onSelect(it) },
            onDismissRequest = { chooseShown = false },
            onCreateRequest = onCreateRequest
        )
    }

    OutlinedButton(onClick = { chooseShown = true }) {
        Text(
            text = if (current == null) stringResource(R.string.select) else current.toString(),
            modifier = modifier
        )
    }
}