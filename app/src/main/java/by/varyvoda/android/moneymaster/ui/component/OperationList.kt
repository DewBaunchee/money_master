package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.ExpenseDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.IncomeDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.OperationDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.TransferDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.model.domain.toDateString
import by.varyvoda.android.moneymaster.data.model.icon.IconRef
import by.varyvoda.android.moneymaster.ui.theme.Negative
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy
import by.varyvoda.android.moneymaster.util.valueOrNull

@Composable
fun TitledOperationList(
    accordingToAccount: AccountDetails,
    operations: List<OperationDetails>,
    onViewAllClick: () -> Unit,
    isSelected: (OperationDetails) -> Boolean,
    onSelect: (OperationDetails) -> Unit,
    onEditClick: (OperationDetails) -> Unit,
    onDeleteClick: (OperationDetails) -> Unit,
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
            accordingToAccount = accordingToAccount,
            operations = operations,
            isSelected = isSelected,
            onSelect = onSelect,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
        )
    }
}

@Composable
fun OperationList(
    accordingToAccount: AccountDetails,
    operations: List<OperationDetails>,
    isSelected: (OperationDetails) -> Boolean,
    onSelect: (OperationDetails) -> Unit,
    onEditClick: (OperationDetails) -> Unit,
    onDeleteClick: (OperationDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    ListPicker(modifier = modifier) {
        items(operations.size) {
            val prevOperation = operations.getOrNull(it - 1)
            val operation = operations[it]

            if (operation.date != prevOperation?.date) {
                Text(text = operation.date.toDateString())
            }

            OperationListItem(
                accordingToAccount = accordingToAccount,
                operation = operation,
                onClick = { onSelect(operation) }
            )
            if (isSelected(operation)) {
                OperationItemEditPanel(
                    operation = operation,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                )
            }
        }
    }
}

@Composable
fun OperationItemEditPanel(
    operation: OperationDetails,
    onEditClick: (OperationDetails) -> Unit,
    onDeleteClick: (OperationDetails) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .zIndex(-1f)
    ) {
        Surface(
            shadowElevation = 1.dp,
            tonalElevation = (-1).dp,
            shape = MaterialTheme.shapes.small.copy(
                topStart = CornerSize(0), topEnd = CornerSize(0)
            ),
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.formSpacedBy(),
                modifier = Modifier.formPadding(),
            ) {
                AppIconButton(
                    onClick = { onEditClick(operation) },
                    iconRef = IconRef.EditOperation,
                    text = null,
                )
                AppIconButton(
                    onClick = { onDeleteClick(operation) },
                    iconRef = IconRef.DeleteOperation,
                    text = null,
                    tint = Negative,
                )
            }
        }
    }

}

@Composable
fun OperationListItem(
    accordingToAccount: AccountDetails,
    operation: OperationDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (operation) {
        is IncomeDetails -> operation.apply {
            val category = category.valueOrNull()
            val account = accounts.valueOrNull()
            val currency = account?.currency?.valueOrNull()
            IncomeExpenseOperationListItem(
                operation = operation,
                income = true,
                category = category,
                description = model.description,
                currency = currency,
                amount = model.amount,
                onClick = onClick,
                modifier = modifier,
            )
        }

        is ExpenseDetails -> operation.apply {
            val category = category.valueOrNull()
            val account = accounts.valueOrNull()
            val currency = account?.currency?.valueOrNull()
            IncomeExpenseOperationListItem(
                operation = operation,
                income = false,
                category = category,
                description = model.description,
                currency = currency,
                amount = model.amount,
                onClick = onClick,
                modifier = modifier,
            )
        }

        is TransferDetails -> TransferOperationListItem(
            accordingToAccount = accordingToAccount,
            operation = operation,
            onClick = onClick,
        )
    }
}

@Composable
fun OperationItemContainer(
    operation: OperationDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        shadowElevation = dimensionResource(R.dimen.soft_elevation),
        shape = MaterialTheme.shapes.small,
    ) {
        ListPickerOption(
            item = operation,
            isSelected = false,
            onClick = onClick,
            modifier = modifier,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.formSpacedBy(),
                content = content,
            )
        }
    }
}

@Composable
fun IncomeExpenseOperationListItem(
    operation: OperationDetails,
    income: Boolean,
    category: Category?,
    description: String,
    currency: Currency?,
    amount: MoneyAmount,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OperationItemContainer(
        operation = operation,
        onClick = onClick,
        modifier = modifier,
    ) {
        IconAndText(icon = { CategoryBox(category = category, onlyIcon = true) }, text = {
            if (category != null) TitleAndText(
                category.name,
                description,
            )
        }, modifier = Modifier.weight(1f)
        )
        MoneyText(
            currency = currency,
            amount = amount,
            negative = !income,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun TransferOperationListItem(
    accordingToAccount: AccountDetails,
    operation: TransferDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val showLikeSent = accordingToAccount.id == operation.model.sourceAccountId
    val currency = accordingToAccount.currency.valueOrNull()

    val relatedAccount = (if (showLikeSent) operation.destinationAccount
    else operation.sourceAccount).valueOrNull()
    val relatedCurrency = relatedAccount?.currency?.valueOrNull()

    OperationItemContainer(
        operation = operation,
        onClick = onClick,
        modifier = modifier,
    ) {
        val textPrefix = stringResource(
            if (showLikeSent) R.string.to
            else R.string.from
        ) + ":"
        IconAndText(icon = { AccountIcon(account = relatedAccount) }, text = {
            Text(text = "$textPrefix ${relatedAccount?.run { model.name } ?: ""}")
            MoneyText(
                currency = relatedCurrency,
                amount = operation.model.receivedAmount,
                negative = !showLikeSent,
            )
        }, modifier = Modifier.weight(1f)
        )
        MoneyText(
            currency = currency,
            amount = operation.model.sentAmount,
            negative = showLikeSent,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
