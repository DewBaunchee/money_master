package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.data.model.domain.Money
import kotlin.math.absoluteValue

@Composable
fun MoneyText(
    currency: Currency?,
    amount: Money?,
    modifier: Modifier = Modifier,
    defaultAmountString: String = "",
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
) {
    val sign = if (amount != null && amount < 0) "-" else ""
    val symbol = currency?.symbol ?: ""
    Text(
        text = "$sign$symbol${amount?.absoluteValue ?: defaultAmountString}",
        color = color,
        style = style,
        modifier = modifier,
    )
}