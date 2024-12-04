package by.varyvoda.android.moneymaster.ui.screen.account.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.toBrush
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.data.service.icons.IconRefMap
import by.varyvoda.android.moneymaster.ui.component.AppButton
import by.varyvoda.android.moneymaster.ui.component.AppIcon
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.ButtonSelector
import by.varyvoda.android.moneymaster.ui.component.ColorThemePickerDialog
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.IconPickerDialog
import by.varyvoda.android.moneymaster.ui.component.MainTopBar
import by.varyvoda.android.moneymaster.ui.component.TitleAndText
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination
import by.varyvoda.android.moneymaster.ui.util.formPadding

object AccountOperationCategoryDestination : NavigationDestination {
    override val route = "operation/category/edit"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountOperationCategoryEditScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountOperationCategoryEditViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            MainTopBar(
                titleId = R.string.new_category,
                onBackClick = { viewModel.onBackClick() })
        },
        modifier = modifier
    ) { innerPadding ->
        Body(
            modifier = Modifier
                .padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
private fun Body(
    modifier: Modifier = Modifier,
    viewModel: AccountOperationCategoryEditViewModel
) {
    val icons = viewModel.icons.collectAsState().value
    val colorThemes = viewModel.colorThemes.collectAsState().value
    val (name, operationType, icon, colorTheme, iconSearchString) = viewModel.uiState.collectAsState().value

    FormBox(
        buttons = listOf {
            AppButton(
                onClick = { viewModel.onSaveClick() },
                enabled = viewModel.canSave(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    ) {
        Column(
            modifier = modifier
                .padding(formPadding()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.form_spaced_by))
        ) {
            AppTextField(
                value = name,
                label = { Text(text = stringResource(R.string.category_edit_name_field_label)) },
                onValueChange = { viewModel.changeName(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.category_type),
                style = MaterialTheme.typography.titleMedium
            )
            ButtonSelector(
                items = listOf(
                    AccountOperation.Type.EXPENSE to R.string.expense,
                    AccountOperation.Type.INCOME to R.string.income
                ),
                isSelected = { it.first == operationType },
                onSelect = { viewModel.changeOperationType(it.first) },
                modifier = Modifier
                    .fillMaxWidth()
            ) { item ->
                Text(
                    text = stringResource(item.second),
                )
            }
            AppearanceBuilder(
                icons = icons,
                themes = colorThemes,
                currentIconRef = icon,
                currentTheme = colorTheme,
                onIconRefSelect = { viewModel.onSelectIcon(it) },
                onThemeSelect = { viewModel.onSelectColorTheme(it) },
                iconSearchString = iconSearchString,
                onIconSearchStringChange = { viewModel.changeIconSearchString(it) }
            )
        }
    }
}

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
        verticalArrangement = Arrangement.spacedBy(formPadding()),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(formPadding()),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.grid_picker_box_size))
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
                    horizontalArrangement = Arrangement.spacedBy(formPadding()),
                    modifier = Modifier
                        .padding(formPadding())
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
                    horizontalArrangement = Arrangement.spacedBy(formPadding()),
                    modifier = Modifier
                        .padding(formPadding())
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
