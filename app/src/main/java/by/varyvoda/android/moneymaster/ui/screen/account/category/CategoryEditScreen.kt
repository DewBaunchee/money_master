package by.varyvoda.android.moneymaster.ui.screen.account.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.AppTitle
import by.varyvoda.android.moneymaster.ui.component.AppearanceBuilder
import by.varyvoda.android.moneymaster.ui.component.ButtonSelector
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.MainTopBar
import by.varyvoda.android.moneymaster.ui.component.SaveButton
import by.varyvoda.android.moneymaster.ui.component.TitledContent
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun CategoryEditScreen(
    viewModel: CategoryEditViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            MainTopBar(
                titleId = R.string.new_category,
                onBackClick = { viewModel.onBackClick() }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Body(
            modifier = Modifier
                .padding(innerPadding),
            viewModel = viewModel,
        )
    }
}

@Composable
private fun Body(
    modifier: Modifier = Modifier,
    viewModel: CategoryEditViewModel
) {
    val icons = viewModel.icons.collectAsState().value
    val colorThemes = viewModel.colorThemes.collectAsState().value
    val (_, name, operationType, icon, colorTheme, iconSearchString) = viewModel.uiState.collectAsState().value

    FormBox(
        buttons = listOf {
            SaveButton(viewModel = viewModel)
        },
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.formSpacedBy(),
            modifier = Modifier.formPadding()
        ) {
            AppTextField(
                value = name,
                label = { Text(text = stringResource(R.string.category_edit_name_field_label)) },
                onValueChange = { viewModel.changeName(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
            )
            TitledContent(
                applyFormPadding = false,
                title = { AppTitle(textId = R.string.category_type) }) {
                ButtonSelector(
                    items = listOf(
                        Operation.Type.EXPENSE to R.string.expense,
                        Operation.Type.INCOME to R.string.income
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
