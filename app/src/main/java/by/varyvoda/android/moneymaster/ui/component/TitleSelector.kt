package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> TitleSelector(
    options: List<T>,
    isSelected: (T) -> Boolean,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable RowScope.(T) -> Unit,
) {
    ButtonSelector(
        items = options,
        isSelected = isSelected,
        onSelect = onSelect,
        modifier = modifier
            .fillMaxWidth(),
        itemContent = itemContent,
    )
}