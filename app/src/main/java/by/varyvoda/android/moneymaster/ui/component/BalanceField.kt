package by.varyvoda.android.moneymaster.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun BalanceField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelId: Int,
    imeAction: ImeAction,
    modifier: Modifier = Modifier
) {
    AppTextField(
        value = value,
        label = { Text(text = stringResource(labelId)) },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        modifier = modifier,
    )
}