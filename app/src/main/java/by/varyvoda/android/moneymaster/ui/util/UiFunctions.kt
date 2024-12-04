package by.varyvoda.android.moneymaster.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.currency.Currency

@Composable
fun formPadding() = dimensionResource(R.dimen.form_padding)

fun makeMoneyString(money: String, currency: Currency?): String {
    return if (currency == null) money else currency.symbol + money
}