package by.varyvoda.android.moneymaster.ui.screen.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.ui.component.CategoriesPickerForm
import by.varyvoda.android.moneymaster.ui.component.MainTopBar
import by.varyvoda.android.moneymaster.ui.component.TitleOperationTypeSelector
import by.varyvoda.android.moneymaster.ui.util.collectValue
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
        viewModel.uiState.collectValue()
    val categories = viewModel.categories.collectValue()

    TitleOperationTypeSelector(
        options = listOf(
            Operation.Type.INCOME,
            Operation.Type.EXPENSE,
        ),
        isSelected = { it == operationType },
        onSelect = { viewModel.selectOperationType(it) },
        modifier = Modifier
            .fillMaxWidth()
            .formPadding()
    )
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