package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import by.varyvoda.android.moneymaster.R

@Composable
fun <T> ButtonSelector(
    items: List<T>,
    isSelected: (T) -> Boolean,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.button_selector_space)),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        items(items) { item ->
            AppButton(
                onClick = { onSelect(item) },
                isSecondary = !isSelected(item),
            ) {
                itemContent(item)
            }
        }
    }
}