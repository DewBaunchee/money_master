package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy
import by.varyvoda.android.moneymaster.util.valueOrNull

@Composable
fun OperationList(
    operations: List<OperationDetails>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_item_space)),
        modifier = modifier,
    ) {
        items(operations) {
            OperationListItem(it)
        }
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(dimensionResource(R.dimen.operation_list_item_space))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.formSpacedBy(),
                modifier = Modifier
                    .weight(1f)
            ) {
                CategoryView(category = category, onlyIcon = true)
                if (category != null)
                    TitleAndText(
                        category.name,
                        accounts?.model?.name ?: ""
                    )
            }
            MoneyText(
                currency = currency,
                amount = if(income) amount else -amount,
                color = if (income) Positive else Negative,
                style = MaterialTheme.typography.titleMedium,
            )
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
        title = {
            AppTitleAndViewAll(
                titleId = R.string.operations,
                onViewAllClick = onViewAllClick
            )
        },
        modifier = modifier,
    ) {
        OperationList(operations = operations)
    }
}