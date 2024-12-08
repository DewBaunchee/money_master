package by.varyvoda.android.moneymaster.ui.screen.account.operation.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.domain.plusHours
import by.varyvoda.android.moneymaster.ui.component.AccountListPickerDialog
import by.varyvoda.android.moneymaster.ui.component.AppButton
import by.varyvoda.android.moneymaster.ui.component.AppDatePicker
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.AppTitle
import by.varyvoda.android.moneymaster.ui.component.BalanceField
import by.varyvoda.android.moneymaster.ui.component.CategoryPickerDialog
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.MainTopBar
import by.varyvoda.android.moneymaster.ui.component.TitledCategoryPicker
import by.varyvoda.android.moneymaster.ui.component.TitledContent
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun EditOperationScreen(
    viewModel: EditOperationViewModel,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            MainTopBar(
                titleId = R.string.add_income_title,
                onBackClick = { viewModel.onBackClick() }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        AddIncomeBody(
            viewModel = viewModel,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun AddIncomeBody(
    viewModel: EditOperationViewModel,
    modifier: Modifier = Modifier,
) {
    val accounts =
        viewModel.accounts.collectAsState().value
    val dateSuggestions =
        viewModel.dateSuggestions.collectAsState().value
    val categories =
        viewModel.categories.collectAsState().value
    val (_, _, amount, date, categoryId, description, _, categorySearchString) =
        viewModel.uiState.collectAsState().value

    val accountDetails = viewModel.currentAccount

    FormBox(
        buttons = listOf {
            AppButton(
                onClick = { viewModel.onSaveClick() },
                enabled = viewModel.canSave(),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            TitledContent(title = { AppTitle(textId = R.string.general) }) {
                Row(
                    horizontalArrangement = Arrangement.formSpacedBy(),
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    var accountSelectionShown by remember { mutableStateOf(false) }
                    AppTextField(
                        value = accountDetails?.account?.name ?: "",
                        onValueChange = {},
                        asButton = true,
                        label = {
                            Text(
                                text = stringResource(
                                    if (accountDetails == null)
                                        R.string.select_account
                                    else
                                        R.string.account
                                )
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = { accountSelectionShown = true }),
                    )
                    if (accountSelectionShown) {
                        AccountListPickerDialog(
                            accounts = accounts,
                            isSelected = { it.id == accountDetails?.id },
                            onSelect = {
                                accountSelectionShown = false
                                viewModel.selectAccount(it.id)
                            },
                            onDismissRequest = { accountSelectionShown = false }
                        ) {
                        }
                    }
                    BalanceField(
                        value = amount,
                        onValueChange = { viewModel.changeAmount(it) },
                        labelId = R.string.amount,
                        imeAction = ImeAction.Done,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                AppDatePicker(
                    date = date,
                    onDateSelected = { viewModel.changeDate(it?.plusHours(-3)) },
                    R.string.select_date,
                    R.string.transaction_date_format,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                LazyRow(
                    horizontalArrangement = Arrangement.formSpacedBy(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    items(dateSuggestions) {
                        val isSelected = it.date == date
                        AppButton(
                            onClick = { viewModel.changeDate(it.date) },
                            isSecondary = !isSelected,
                        ) {
                            Text(text = it.label)
                        }
                    }
                }
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

