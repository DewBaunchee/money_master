package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.service.icons.IconRefMap
import by.varyvoda.android.moneymaster.ui.util.formPadding

@Composable
fun FullScreenIconPicker(
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
    searchEnabled: Boolean = true,
    searchString: String = "",
    onSearchStringChange: (String) -> Unit = {},
) {
    BottomDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Text(
            text = stringResource(R.string.icon),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(formPadding()),
        )
        if (searchEnabled) {
            SearchField(
                searchString = searchString,
                onSearchStringChange = onSearchStringChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(formPadding())
            )
        }
        LazyColumn {
            items(iconRefMap.entries.toList()) {
                Text(
                    text = it.key,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(formPadding()),
                )
                FullScreenIconPicker(
                    icons = it.value,
                    isSelected = isSelected,
                    onSelect = onSelect,
                    modifier = Modifier
                        .heightIn(max = 1000.dp)
                        .padding(),
                )
            }
        }
    }
}