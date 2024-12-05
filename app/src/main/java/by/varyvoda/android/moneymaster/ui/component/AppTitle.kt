package by.varyvoda.android.moneymaster.ui.component

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun AppTitle(
    @StringRes textId: Int? = null,
    modifier: Modifier = Modifier,
    text: String? = textId?.let { stringResource(it) }
) {
    Text(
        text = text ?: "",
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
    )
}