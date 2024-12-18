package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.service.icons.IconRefMap

@Composable
fun IconPicker(
    icons: List<IconRef>,
    isSelected: (IconRef) -> Boolean,
    onSelect: (IconRef) -> Unit,
    modifier: Modifier = Modifier
) {
    GridPicker(
        items = icons,
        modifier = modifier,
    ) {
        IconButton(
            onClick = { onSelect(it) },
            isSelected = isSelected(it),
            iconRef = it,
        )
    }
}

@Composable
fun CategorizedIconPicker(
    iconRefMap: IconRefMap,
    isSelected: (IconRef) -> Boolean,
    onSelect: (IconRef) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(iconRefMap.entries.toList()) {
            TitledContent(applyFormPadding = false, title = { AppTitle(text = it.key) }) {
                IconPicker(
                    icons = it.value,
                    isSelected = isSelected,
                    onSelect = onSelect,
                    modifier = Modifier
                        .heightIn(max = 1000.dp),
                )
            }
        }
    }
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    isSelected: Boolean,
    iconRef: IconRef,
    modifier: Modifier = Modifier
) {
    AppIconButton(
        onClick = onClick,
        isSecondary = !isSelected,
        modifier = modifier,
        text = iconRef.label,
        iconRef = iconRef,
    )
}

@Composable
fun IconPickerDialog(
    iconRefMap: IconRefMap,
    isSelected: (IconRef) -> Boolean,
    onSelect: (IconRef) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    searchEnabled: Boolean = true,
    searchString: String = "",
    onSearchStringChange: (String) -> Unit = {},
) {
    TitledSearchableBottomDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        searchEnabled = searchEnabled,
        searchString = searchString,
        onSearchStringChange = onSearchStringChange,
    ) {
        CategorizedIconPicker(
            iconRefMap = iconRefMap,
            isSelected = isSelected,
            onSelect = onSelect
        )
    }
}