package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.model.icon.IconRef.Companion.toIconRef

@Composable
fun <T> ListDialog(
    items: List<T>,
    isSelected: (T) -> Boolean,
    onSelect: (T) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    searchEnabled: Boolean = false,
    searchString: String = "",
    onSearchStringChange: (String) -> Unit = {},
    itemContent: @Composable (item: T) -> Unit,
) {
    BottomDialog(onDismissRequest) {
        Column(
            modifier = modifier
                .padding(dimensionResource(R.dimen.card_padding))
        ) {
            if (searchEnabled) {
                SearchField(
                    searchString = searchString,
                    onSearchStringChange = onSearchStringChange
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_dialog_item_space)),
                modifier = Modifier
            ) {
                this.items(items) {
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = true, onClick = { onSelect(it) })
                                .padding(dimensionResource(R.dimen.list_dialog_item_padding))
                        ) {
                            itemContent(it)
                            if (isSelected(it)) {
                                AppIcon(
                                    iconRef = Icons.Filled.Done.toIconRef(),
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}