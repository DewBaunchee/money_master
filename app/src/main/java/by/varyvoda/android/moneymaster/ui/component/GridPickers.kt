package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> CompactGridPicker(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
) {
    LazyHorizontalGrid(
        GridCells.Fixed(1),
//        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.grid_picker_space)),
//        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.grid_picker_space)),
        modifier = modifier
    ) {
        this.items(items, itemContent = itemContent)
    }
}

@Composable
fun <T> GridPicker(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
) {
    LazyVerticalGrid(
        GridCells.Fixed(4),
//        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.grid_picker_space)),
//        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.grid_picker_space)),
        modifier = modifier,
    ) {
        this.items(items, itemContent = itemContent)
    }
}