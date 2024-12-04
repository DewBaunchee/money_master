package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.toBrush
import by.varyvoda.android.moneymaster.ui.util.formPadding

@Composable
fun FullScreenColorThemePicker(
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
        modifier = modifier
            .padding(formPadding()),
        text = theme.name,
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
        Text(
            text = stringResource(R.string.background),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(formPadding()),
        )
        FullScreenColorThemePicker(
            themes = themes,
            isSelected = isSelected,
            onSelect = onSelect,
            modifier = Modifier
                .padding(formPadding()),
        )
    }
}