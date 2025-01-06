package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation

private val operationTypeToStringRes = mapOf(
    Operation.Type.BALANCE_EDIT to R.string.balance_edit,
    Operation.Type.EXPENSE to R.string.expense,
    Operation.Type.INCOME to R.string.income,
    Operation.Type.TRANSFER to R.string.transfer,
)

@Composable
private fun getString(operationType: Operation.Type) =
    stringResource(operationTypeToStringRes[operationType]!!)

@Composable
fun OperationTypeSelector(
    options: List<Operation.Type>,
    isSelected: (Operation.Type) -> Boolean,
    onSelect: (Operation.Type) -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonSelector(
        options = options,
        isSelected = isSelected,
        onSelect = onSelect,
        modifier = modifier,
    ) { Text(text = getString(it)) }
}

@Composable
fun TitleOperationTypeSelector(
    options: List<Operation.Type>,
    isSelected: (Operation.Type) -> Boolean,
    onSelect: (Operation.Type) -> Unit,
    modifier: Modifier = Modifier,
) {
    TitleSelector(
        options = options,
        isSelected = isSelected,
        onSelect = onSelect,
        modifier = modifier,
    ) { Text(text = getString(it)) }
}