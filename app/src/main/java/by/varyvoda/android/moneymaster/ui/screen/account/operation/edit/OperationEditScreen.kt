package by.varyvoda.android.moneymaster.ui.screen.account.operation.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.ui.component.MainTopBar

@Composable
fun OperationEditScreen(viewModel: OperationEditViewModel) {
    val operationType = viewModel.operationType.collectAsState(null).value

    Column {
        MainTopBar(
            titleId = when (operationType) {
                Operation.Type.INCOME -> R.string.add_income_title
                Operation.Type.EXPENSE -> R.string.add_expense_title
                Operation.Type.TRANSFER -> R.string.add_transfer
                else -> R.string.empty_string
            },
            onBackClick = { viewModel.onBackClick() }
        )
        when (operationType) {
            Operation.Type.INCOME -> IncomeExpenseEditBody(viewModel = viewModel.incomeViewModel)
            Operation.Type.EXPENSE -> IncomeExpenseEditBody(viewModel = viewModel.expenseViewModel)
            Operation.Type.TRANSFER -> TransferEditBody(viewModel = viewModel.transferViewModel)
            else -> Text("Not implemented")
        }
    }
}
