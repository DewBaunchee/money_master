package by.varyvoda.android.moneymaster.ui.screen.account.operation.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.ui.component.AccountAndAmount
import by.varyvoda.android.moneymaster.ui.component.AppDatePicker
import by.varyvoda.android.moneymaster.ui.component.AppTextField
import by.varyvoda.android.moneymaster.ui.component.AppTitle
import by.varyvoda.android.moneymaster.ui.component.DateSuggestions
import by.varyvoda.android.moneymaster.ui.component.FormBox
import by.varyvoda.android.moneymaster.ui.component.SaveButton
import by.varyvoda.android.moneymaster.ui.component.TitledContent
import by.varyvoda.android.moneymaster.ui.util.formPadding
import by.varyvoda.android.moneymaster.ui.util.formSpacedBy

@Composable
fun TransferEditBody(viewModel: TransferViewModel, modifier: Modifier = Modifier) {
    val accounts =
        viewModel.accounts.collectAsState().value
    val dateSuggestions =
        viewModel.dateSuggestions.collectAsState().value
    val (date, _, _, sentAmount, receivedAmount, description) =
        viewModel.uiState.collectAsState().value

    val sourceAccount = viewModel.sourceAccount
    val destinationAccount = viewModel.destinationAccount

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
            TitledContent(title = { AppTitle(textId = R.string.from) }) {
                AccountAndAmount(
                    accounts = accounts,
                    account = sourceAccount,
                    onSelectAccount = { viewModel.selectSourceAccount(it.id) },
                    amount = sentAmount,
                    onAmountChange = { viewModel.changeSentAmount(it) }
                )
            }
            TitledContent(title = { AppTitle(textId = R.string.to) }) {
                AccountAndAmount(
                    accounts = accounts,
                    account = destinationAccount,
                    onSelectAccount = { viewModel.selectDestinationAccount(it.id) },
                    amount = receivedAmount,
                    onAmountChange = { viewModel.changeReceivedAmount(it) }
                )
            }
            Column(
                verticalArrangement = Arrangement.formSpacedBy(),
                modifier = Modifier
                    .formPadding(),
            ) {
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
        }
    }
}

