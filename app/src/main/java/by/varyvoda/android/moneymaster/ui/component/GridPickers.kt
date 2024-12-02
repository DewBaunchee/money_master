package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R

@Composable
fun TitledPicker(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    onViewAllClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.form_padding))
        ) {
            title()
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.view_all),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .clickable(onClick = onViewAllClick)
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.form_spaced_by)))
        content()
    }
}

@Composable
fun <T> CompactGridPicker(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
) {
    LazyHorizontalGrid(
        GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.grid_picker_space)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.grid_picker_space)),
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