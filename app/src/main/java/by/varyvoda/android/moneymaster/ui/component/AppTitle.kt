package by.varyvoda.android.moneymaster.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.ui.util.formPadding

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

@Composable
fun AppTitleAndViewAll(
    @StringRes titleId: Int,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        AppTitle(textId = titleId)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.view_all),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onViewAllClick,
                )
        )
    }
}