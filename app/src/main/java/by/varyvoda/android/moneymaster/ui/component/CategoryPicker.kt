package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperationCategory
import by.varyvoda.android.moneymaster.util.toBrush

@Composable
fun CategoryPicker(
    categories: List<AccountOperationCategory>,
    isSelected: (AccountOperationCategory) -> Boolean,
    onSelect: (AccountOperationCategory) -> Unit,
    modifier: Modifier = Modifier,
    onViewAllClick: () -> Unit = {},
) {
    TitledPicker(
        title = {
            Text(
                text = stringResource(R.string.category),
                style = MaterialTheme.typography.titleMedium
            )
        },
        modifier = modifier,
        onViewAllClick = onViewAllClick,
    ) {
        CompactGridPicker(
            items = categories,
            modifier = Modifier
                .height(110.dp)
        ) {
            CategoryButton(
                onClick = { onSelect(it) },
                isSelected = isSelected(it),
                category = it,
            )
        }
    }
}

@Composable
fun FullScreenCategoryPicker(
    categories: List<AccountOperationCategory>,
    isSelected: (AccountOperationCategory) -> Boolean,
    onSelect: (AccountOperationCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    GridPicker(
        items = categories,
        modifier = modifier,
    ) {
        CategoryButton(
            onClick = { onSelect(it) },
            isSelected = isSelected(it),
            category = it,
        )
    }
}

@Composable
fun CategoryButton(
    onClick: () -> Unit,
    isSelected: Boolean,
    category: AccountOperationCategory,
    modifier: Modifier = Modifier
) {
    AppIconButton(
        onClick = onClick,
        isSelected = isSelected,
        modifier = modifier
            .padding(dimensionResource(R.dimen.form_padding)),
        text = category.name,
        imageVector = null,
        background = category.color.toBrush(),
    )
}