package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun <T> TitleSelector(
    options: List<T>,
    isSelected: (T) -> Boolean,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable RowScope.(T) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.formSpacedBy(),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        options.forEach {
            AppButton(
                onClick = { onSelect(it) },
                isSecondary = !isSelected(it),
                modifier = Modifier.weight(1f),
            ) {
                itemContent(it)
            }
        }
    }
}