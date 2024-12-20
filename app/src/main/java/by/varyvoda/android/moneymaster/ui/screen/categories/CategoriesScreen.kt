package by.varyvoda.android.moneymaster.ui.screen.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.ui.component.CategoriesPickerForm
import by.varyvoda.android.moneymaster.ui.component.MainTopBar
import by.varyvoda.android.moneymaster.ui.component.TitleSelector
import by.varyvoda.android.moneymaster.ui.util.formPadding

@Composable
fun CategoriesScreen(viewModel: CategoriesViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        MainTopBar(
            titleId = R.string.categories,
            onBackClick = { viewModel.onBackClick() }
        )
        CategoriesScreenBody(viewModel = viewModel, modifier = modifier)
    }
}

@Composable
fun CategoriesScreenBody(viewModel: CategoriesViewModel, modifier: Modifier = Modifier) {
    val (categorySearchString, operationType) =
        viewModel.uiState.collectAsState().value
    val categories = viewModel.categories.collectAsState().value

    TitleSelector(
        options = listOf(
            Operation.Type.INCOME to R.string.income,
            Operation.Type.EXPENSE to R.string.expense,
        ),
        isSelected = { it.first == operationType },
        onSelect = { viewModel.selectOperationType(it.first) },
        modifier = Modifier
            .fillMaxWidth()
            .formPadding()
    ) {
        Text(text = stringResource(it.second), modifier = Modifier.fillMaxWidth())
    }
    CategoriesPickerForm(
        categories = categories,
        isSelected = { false },
        onSelect = { viewModel.onCategoryClick(it.id) },
        searchString = categorySearchString,
        onSearchStringChange = { viewModel.changeCategorySearchString(it) },
        onAddCategoryClick = { viewModel.onAddCategoryClick() },
        onClose = { viewModel.onBackClick() },
        modifier = modifier,
    )
}