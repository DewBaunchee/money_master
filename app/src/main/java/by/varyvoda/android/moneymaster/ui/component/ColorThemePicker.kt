package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.toBrush

@Composable
fun ColorThemePicker(
    themes: List<ColorTheme>,
    isSelected: (ColorTheme) -> Boolean,
    onSelect: (ColorTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    GridPicker(
        items = themes,
        modifier = modifier,
    ) {
        ColorThemeButton(
            onClick = { onSelect(it) },
            isSelected = isSelected(it),
            theme = it,
        )
    }
}

@Composable
fun ColorThemeButton(
    onClick: () -> Unit,
    isSelected: Boolean,
    theme: ColorTheme,
    modifier: Modifier = Modifier
) {
    AppIconButton(
        onClick = onClick,
        isSelected = isSelected,
        modifier = modifier,
        text = theme.name,
        tint = MaterialTheme.colorScheme.secondary,
        background = theme.colors.toBrush(),
    )
}

@Composable
fun ColorThemePickerDialog(
    themes: List<ColorTheme>,
    isSelected: (ColorTheme) -> Boolean,
    onSelect: (ColorTheme) -> Unit,
    onDismissRequest: () -> Unit,
) {
    BottomDialog(
        onDismissRequest = onDismissRequest,
    ) {
        TitledContent(title = { AppTitle(textId = R.string.background) }) {
            ColorThemePicker(
                themes = themes,
                isSelected = isSelected,
                onSelect = onSelect,
            )
        }
    }
}