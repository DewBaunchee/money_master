package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R


@Composable
fun SearchField(
    searchString: String,
    onSearchStringChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AppTextField(
        value = searchString,
        onValueChange = onSearchStringChange,
        placeholder = { Text(stringResource(R.string.search)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search)
            )
        },
        modifier = modifier,
    )
}
