package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency
import by.varyvoda.android.moneymaster.util.makeMoneyString

private const val CARD_HEIGHT_TO_WIDTH_RATIO = 0.55f

@Composable
fun AccountCard(
    icon: ImageVector,
    theme: AccountTheme,
    title: String,
    balance: String,
    currency: Currency?,
    modifier: Modifier = Modifier
) {
    var cardWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Card(
        modifier = modifier
            .height(cardWidth * CARD_HEIGHT_TO_WIDTH_RATIO)
            .onSizeChanged {
                val newCardWidth = density.run { it.width.toDp() }
                if (newCardWidth != cardWidth) cardWidth = newCardWidth
            }
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = theme.gradientColors,
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                )
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.card_padding))
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small),
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(R.string.account_icon),
                            tint = Color.White,
                            modifier = Modifier
                                .background(Color(1f, 1f, 1f, 0.2f))
                                .padding(4.dp)
                        )
                    }
                    Text(
                        text = if (title.isBlank())
                            stringResource(R.string.account_creation_default_card_title)
                        else
                            title,
                        color = Color.White
                    )
                }
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = if (balance.isBlank()) "" else stringResource(R.string.amount),
                            color = Color(0xFFF1F3F6),
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = makeMoneyString(balance, currency),
                            color = Color.White,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                    if (currency != null) {
                        Text(
                            text = currency.code,
                            color = Color(0xFFF1F3F6),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

