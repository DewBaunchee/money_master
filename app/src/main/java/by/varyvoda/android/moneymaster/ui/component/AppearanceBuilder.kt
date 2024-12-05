package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.toBrush
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.service.icons.IconRefMap
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun AppearanceBuilder(
    icons: IconRefMap,
    themes: List<ColorTheme>,
    currentIconRef: IconRef,
    currentTheme: ColorTheme,
    onIconRefSelect: (IconRef) -> Unit,
    onThemeSelect: (ColorTheme) -> Unit,
    iconSearchString: String,
    onIconSearchStringChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.formSpacedBy(),
        modifier = modifier
            .formPadding(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.formSpacedBy(),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.button_box_size))
                    .background(currentTheme.colors.toBrush(), MaterialTheme.shapes.small)
            ) {
                AppIcon(
                    iconRef = currentIconRef,
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
            TitleAndText(
                stringResource(R.string.appearance),
                stringResource(R.string.appearance_description)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium),
        ) {
            var backgroundShown by remember { mutableStateOf(false) }
            if (backgroundShown) {
                ColorThemePickerDialog(
                    themes = themes,
                    isSelected = { it == currentTheme },
                    onSelect = onThemeSelect,
                    onDismissRequest = { backgroundShown = false },
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { backgroundShown = true }),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.formSpacedBy(),
                    modifier = Modifier
                        .formPadding()
                ) {
                    Box(
                        modifier = Modifier
                            .background(currentTheme.colors.toBrush(), MaterialTheme.shapes.small)
                            .size(dimensionResource(R.dimen.large_icon_size))
                    ) {

                    }
                    TitleAndText(
                        stringResource(R.string.background),
                        currentTheme.name,
                    )
                }
            }

            var iconsShown by remember { mutableStateOf(false) }
            if (iconsShown) {
                IconPickerDialog(
                    iconRefMap = icons,
                    isSelected = { it == currentIconRef },
                    onSelect = onIconRefSelect,
                    onDismissRequest = { iconsShown = false },
                    searchString = iconSearchString,
                    onSearchStringChange = onIconSearchStringChange,
                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { iconsShown = true }),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.formSpacedBy(),
                    modifier = Modifier
                        .formPadding()
                ) {
                    AppIcon(
                        iconRef = currentIconRef,
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.large_icon_size))
                    )
                    TitleAndText(
                        stringResource(R.string.icon),
                        currentIconRef.label,
                    )
                }
            }
        }
    }
}
