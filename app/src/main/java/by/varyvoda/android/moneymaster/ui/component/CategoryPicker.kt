package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.domain.toBrush
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.plus

@Composable
fun CategoryPicker(
    categories: List<Category>,
    isSelected: (Category) -> Boolean,
    onSelect: (Category) -> Unit,
    contentPadding: PaddingValues = PaddingValues(formPadding()),
    modifier: Modifier = Modifier,
) {
    GridPicker(
        items = categories,
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        CategoryBox(
            category = it,
            onClick = { onSelect(it) },
            isSelected = isSelected(it),
        )
    }
}

@Composable
fun CategoryBox(
    category: Category?,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    isSelected: Boolean = false,
    onlyIcon: Boolean = false,
) {
    AppIconButton(
        onClick = onClick,
        isSelected = isSelected,
        modifier = modifier,
        text = if (onlyIcon) "" else category?.name,
        iconRef = category?.iconRef,
        tint = MaterialTheme.colorScheme.secondary,
        background = category?.run { colorTheme.colors.toBrush() },
    )
}

@Composable
fun CompactCategoryPicker(
    categories: List<Category>,
    isSelected: (Category) -> Boolean,
    onSelect: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    CompactGridPicker(
        items = categories,
        modifier = modifier
            .height(100.dp)
    ) {
        CategoryBox(
            category = it,
            onClick = { onSelect(it) },
            isSelected = isSelected(it),
        )
    }
}

@Composable
fun TitledCategoryPicker(
    categories: List<Category>,
    isSelected: (Category) -> Boolean,
    onSelect: (Category) -> Unit,
    modifier: Modifier = Modifier,
    onViewAllClick: () -> Unit = {},
) {
    TitledContent(
        applyFormPadding = false,
        title = {
            AppTitleAndViewAll(
                titleId = R.string.category,
                onViewAllClick = onViewAllClick,
                modifier = Modifier.padding(horizontal = formPadding()),
            )
        },
        modifier = modifier,
    ) {
        CompactCategoryPicker(
            categories = categories,
            isSelected = isSelected,
            onSelect = onSelect,
        )
    }
}

@Composable
fun CategoriesPickerForm(
    categories: List<Category>,
    isSelected: (Category) -> Boolean,
    onSelect: (Category) -> Unit,
    searchString: String,
    onSearchStringChange: (String) -> Unit,
    onAddCategoryClick: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FormBox(
        buttons = listOf(
            {
                AppButton(
                    onClick = onAddCategoryClick,
                    isSecondary = true,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.add_category))
                }
            },
            {
                AppButton(
                    onClick = onClose,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.close))
                }
            }
        ),
        modifier = modifier,
    ) { bottomSectionHeightDp ->
        SearchableContent(
            searchEnabled = true,
            searchString = searchString,
            onSearchStringChange = onSearchStringChange,
            modifier = Modifier.formPadding(),
        ) {
            CategoryPicker(
                categories = categories,
                isSelected = isSelected,
                onSelect = onSelect,
                contentPadding = PaddingValues(vertical = formPadding()) +
                        PaddingValues(bottom = bottomSectionHeightDp)
            )
        }
    }
}

@Composable
fun CategoryPickerDialog(
    categories: List<Category>,
    isSelected: (Category) -> Boolean,
    onSelect: (Category) -> Unit,
    onAddCategoryClick: () -> Unit,
    onClose: () -> Unit,
    searchString: String,
    onSearchStringChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FullScreenDialog(
        titleId = R.string.categories,
        onBackClick = onClose,
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            CategoriesPickerForm(
                categories = categories,
                isSelected = isSelected,
                onSelect = onSelect,
                searchString = searchString,
                onSearchStringChange = onSearchStringChange,
                onAddCategoryClick = onAddCategoryClick,
                onClose = onClose,
            )
        }
    }
}
