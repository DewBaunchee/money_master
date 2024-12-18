package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.ExpenseDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.IncomeDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.OperationDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.Money
import by.varyvoda.android.moneymaster.ui.theme.Negative
import by.varyvoda.android.moneymaster.ui.theme.Positive
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy
import by.varyvoda.android.moneymaster.util.valueOrNull

@Composable
fun OperationList(
    operations: List<OperationDetails>,
    modifier: Modifier = Modifier,
) {
    ListPicker(
        items = operations,
        modifier = modifier,
    ) {
        OperationListItem(it)
    }
}

@Composable
fun OperationListItem(
    operation: OperationDetails,
    modifier: Modifier = Modifier,
) {
    when (operation) {
        is IncomeDetails -> operation.apply {
            val category = category.valueOrNull()
            val accounts = accounts.valueOrNull()
            val currency = accounts?.currency?.valueOrNull()
            IncomeExpenseOperationListItem(
                operation = operation,
                income = true,
                category = category,
                accounts = accounts,
                currency = currency,
                amount = model.amount,
                modifier = modifier,
            )
        }

        is ExpenseDetails -> operation.apply {
            val category = category.valueOrNull()
            val accounts = accounts.valueOrNull()
            val currency = accounts?.currency?.valueOrNull()
            IncomeExpenseOperationListItem(
                operation = operation,
                income = false,
                category = category,
                accounts = accounts,
                currency = currency,
                amount = model.amount,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun IncomeExpenseOperationListItem(
    operation: OperationDetails,
    income: Boolean,
    category: Category?,
    accounts: AccountDetails?,
    currency: Currency?,
    amount: Money,
    modifier: Modifier = Modifier,
) {
    Surface(
        shadowElevation = dimensionResource(R.dimen.soft_elevation),
        shape = MaterialTheme.shapes.small,
    ) {
        ListPickerOption(
            item = operation,
            isSelected = false,
            onClick = {},
            modifier = modifier,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.formSpacedBy(),
                modifier = Modifier
            ) {
                IconAndText(
                    icon = { CategoryBox(category = category, onlyIcon = true) },
                    text = {
                        if (category != null)
                            TitleAndText(
                                category.name,
                                accounts?.model?.name ?: ""
                            )
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                MoneyText(
                    currency = currency,
                    amount = if (income) amount else -amount,
                    color = if (income) Positive else Negative,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Composable
fun TitledOperationList(
    operations: List<OperationDetails>,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TitledContent(
        applyFormPadding = false,
        title = {
            AppTitleAndViewAll(
                titleId = R.string.operations,
                onViewAllClick = onViewAllClick,
                modifier = Modifier.padding(
                    top = formPadding(),
                    start = formPadding(),
                    end = formPadding(),
                ),
            )
        },
        modifier = modifier,
    ) {
        OperationList(
            operations = operations,
        )
    }
}