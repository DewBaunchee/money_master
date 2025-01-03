package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.ui.util.formPadding

@Composable
fun ListPicker(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(dimensionResource(R.dimen.list_item_space)),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentPadding: PaddingValues = PaddingValues(formPadding()),
    content: LazyListScope.() -> Unit,
) {
    LazyColumn(
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        modifier = modifier,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun <T> ListPicker(
    items: List<T>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(dimensionResource(R.dimen.list_item_space)),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentPadding: PaddingValues = PaddingValues(formPadding()),
    itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    ListPicker(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        contentPadding = contentPadding
    ) {
        items(items = items, itemContent = itemContent)
    }
}

@Composable
fun <T> ListPickerOption(
    item: T,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    itemContent: @Composable BoxScope.(item: T) -> Unit,
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = true, onClick = onClick)
                .padding(dimensionResource(R.dimen.list_item_padding))
        ) {
            itemContent(item)
            if (isSelected) {
                AppIcon(
                    iconRef = IconRef.Selected,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}