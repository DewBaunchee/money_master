package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R

@OptIn(ExperimentalMaterial3Api::class)
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
    val sheetState = rememberModalBottomSheetState()

    val screenHeight = LocalConfiguration.current.screenHeightDp

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
            .heightIn(min = (screenHeight * 0.3f).dp, max = (screenHeight * 0.5f).dp)
    ) {
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
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = null,
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