package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.ui.theme.Negative
import by.varyvoda.android.moneymaster.ui.theme.Positive

@Composable
fun MoneyText(
    currency: Currency?,
    amount: MoneyAmount?,
    modifier: Modifier = Modifier,
    defaultAmountString: String = "",
    negative: Boolean? = amount?.isNegative(),
    color: Color = negative?.let { if (negative) Negative else Positive }
        ?: MaterialTheme.colorScheme.primary,
    style: TextStyle = LocalTextStyle.current,
) {
    val sign = if (negative == true) "-" else ""
    val symbol = currency?.symbol ?: ""
    val amountString = amount?.toString(absolute = true) ?: defaultAmountString
    Text(
        text = "$sign$symbol$amountString",
        color = color,
        style = style,
        modifier = modifier,
    )
}