package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun <T> ButtonSelector(
    options: List<T>,
    isSelected: (T) -> Boolean,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable RowScope.(T) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.formSpacedBy(),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        items(options) { item ->
            AppButton(
                onClick = { onSelect(item) },
                isSecondary = !isSelected(item),
            ) {
                itemContent(item)
            }
        }
    }
}