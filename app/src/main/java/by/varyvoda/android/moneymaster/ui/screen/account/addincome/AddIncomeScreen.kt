package by.varyvoda.android.moneymaster.ui.screen.account.addincome

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperationCategory
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.plusHours
import by.varyvoda.android.moneymaster.ui.component.AppButton
import by.varyvoda.android.moneymaster.ui.component.AppDatePicker
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.BalanceField
import by.varyvoda.android.moneymaster.ui.component.CategoryPicker
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.FullScreenCategoryPicker
import by.varyvoda.android.moneymaster.ui.component.FullScreenDialog
import by.varyvoda.android.moneymaster.ui.component.ListDialog
import by.varyvoda.android.moneymaster.ui.component.MainTopBar
import by.varyvoda.android.moneymaster.ui.component.SearchField
import by.varyvoda.android.moneymaster.ui.navigation.NavigationDestination
import by.varyvoda.android.moneymaster.ui.util.formPadding

object AddIncomeDestination : NavigationDestination {
    const val ACCOUNT_ID_ROUTE_ARG = "accountId"
    override val route = "income/add?$ACCOUNT_ID_ROUTE_ARG={$ACCOUNT_ID_ROUTE_ARG}"

    fun route(accountId: Id?): String {
        return "income/add?$ACCOUNT_ID_ROUTE_ARG=$accountId"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AddIncomeViewModel = viewModel()
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
    viewModel: AddIncomeViewModel,
    modifier: Modifier = Modifier,
) {
    val accounts =
        viewModel.accounts.collectAsState().value
    val dateSuggestions =
        viewModel.dateSuggestions.collectAsState().value
    val categories =
        viewModel.categories.collectAsState().value
    val (_, amount, date, categoryId, description, images, categorySearchString) =
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
        Column {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.form_spaced_by)),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(formPadding()),
            ) {
                Text(
                    stringResource(R.string.general),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.form_spaced_by)),
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
                        ListDialog(
                            items = accounts,
                            isSelected = { it.id == accountDetails?.id },
                            onSelect = {
                                accountSelectionShown = false
                                viewModel.selectAccount(it.id)
                            },
                            onDismissRequest = { accountSelectionShown = false }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.icon_and_label_row_padding))
                            ) {
                                Text(text = it.account.name)
                            }
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
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.form_spaced_by)),
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

                Text(
                    stringResource(R.string.details),
                    style = MaterialTheme.typography.titleMedium
                )
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
    categories: List<AccountOperationCategory>,
    isSelected: (AccountOperationCategory) -> Boolean,
    onSelect: (AccountOperationCategory) -> Unit,
    onAddCategoryClick: () -> Unit,
    searchString: String,
    onSearchStringChange: (String) -> Unit,
) {
    var allCategoriesShown by remember { mutableStateOf(false) }
    CategoryPicker(
        categories = categories,
        isSelected = isSelected,
        onSelect = onSelect,
        onViewAllClick = { allCategoriesShown = true }
    )
    if (allCategoriesShown) {
        FullScreenCategoriesPickerDialog(
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

@Composable
fun FullScreenCategoriesPickerDialog(
    categories: List<AccountOperationCategory>,
    isSelected: (AccountOperationCategory) -> Boolean,
    onSelect: (AccountOperationCategory) -> Unit,
    onAddCategoryClick: () -> Unit,
    onClose: () -> Unit,
    searchString: String,
    onSearchStringChange: (String) -> Unit,
) {
    FullScreenDialog(
        titleId = R.string.categories,
        onBackClick = onClose,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(formPadding()),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(formPadding()),
            ) {
                SearchField(
                    searchString = searchString,
                    onSearchStringChange = onSearchStringChange,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                FormBox(
                    buttons = listOf(
                        {
                            AppButton(
                                onClick = onAddCategoryClick,
                                isSecondary = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(text = stringResource(R.string.add_category))
                            }
                        },
                        {
                            AppButton(
                                onClick = onClose,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(text = stringResource(R.string.close))
                            }
                        }
                    )
                ) {
                    FullScreenCategoryPicker(
                        categories = categories,
                        isSelected = isSelected,
                        onSelect = onSelect,
                    )
                }
            }
        }
    }
}