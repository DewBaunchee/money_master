package by.varyvoda.android.moneymaster.ui.screen.account.operation.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.ui.component.AccountAndAmount
import by.varyvoda.android.moneymaster.ui.component.AppButton
import by.varyvoda.android.moneymaster.ui.component.AppDatePicker
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.AppTitle
import by.varyvoda.android.moneymaster.ui.component.CategoryPickerDialog
import by.varyvoda.android.moneymaster.ui.component.DateSuggestions
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.SaveButton
import by.varyvoda.android.moneymaster.ui.component.TitledCategoryPicker
import by.varyvoda.android.moneymaster.ui.component.TitledContent


@Composable
fun IncomeExpenseEditBody(
    viewModel: IncomeExpenseEditViewModel,
    modifier: Modifier = Modifier,
) {
    val accounts =
        viewModel.accounts.collectAsState().value
    val dateSuggestions =
        viewModel.dateSuggestions.collectAsState().value
    val categories =
        viewModel.categories.collectAsState().value
    val (_, amount, date, categoryId, description, _, categorySearchString) =
        viewModel.uiState.collectAsState().value

    val currentAccount = viewModel.currentAccount

    FormBox(
        buttons = listOf {
            SaveButton(viewModel = viewModel)
        },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            TitledContent(title = { AppTitle(textId = R.string.general) }) {
                AccountAndAmount(
                    accounts = accounts,
                    account = currentAccount,
                    onSelectAccount = { viewModel.selectAccount(it.id) },
                    amount = amount,
                    onAmountChange = { viewModel.changeAmount(it) },
                )
                AppDatePicker(
                    date = date,
                    onDateSelected = { viewModel.changeDate(it) },
                    R.string.select_date,
                    R.string.transaction_date_format,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                DateSuggestions(
                    dateSuggestions = dateSuggestions,
                    date = date,
                    onDateSelected = { viewModel.changeDate(it) },
                )
            }

            TitledContent(title = { AppTitle(textId = R.string.details) }) {
                AppTextField(
                    value = description,
                    onValueChange = { viewModel.changeDescription(it) },
                    label = { Text(text = stringResource(R.string.description)) },
                    singleLine = false,
                    minLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            CategoryPickerSection(
                categories = categories,
                isSelected = { it.id == categoryId },
                onSelect = { viewModel.changeCategory(it.id) },
                onAddCategoryClick = { viewModel.onAddCategoryClick() },
                searchString = categorySearchString,
                onSearchStringChange = { viewModel.changeCategorySearchString(it) }
            )
        }
    }
}

@Composable
fun CategoryPickerSection(
    categories: List<Category>,
    isSelected: (Category) -> Boolean,
    onSelect: (Category) -> Unit,
    onAddCategoryClick: () -> Unit,
    searchString: String,
    onSearchStringChange: (String) -> Unit,
) {
    var allCategoriesShown by remember { mutableStateOf(false) }
    TitledCategoryPicker(
        categories = categories,
        isSelected = isSelected,
        onSelect = onSelect,
        onViewAllClick = { allCategoriesShown = true }
    )
    if (allCategoriesShown) {
        CategoryPickerDialog(
            categories = categories,
            isSelected = isSelected,
            onSelect = onSelect,
            onAddCategoryClick = onAddCategoryClick,
            onClose = { allCategoriesShown = false },
            searchString = searchString,
            onSearchStringChange = onSearchStringChange,
        )
    }
}

