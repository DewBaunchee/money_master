package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import by.varyvoda.android.moneymaster.R

@Composable
fun <T> SelectDialog(
    items: List<T>,
    current: T?,
    onSelect: (T) -> Unit,
    onDismissRequest: () -> Unit,
    onCreateRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card {
            Column(
                modifier = modifier
                    .padding(dimensionResource(R.dimen.card_padding))
            ) {
                LazyColumn(
                    modifier = Modifier
                ) {
                    this.items(items) {
                        OutlinedButton(
                            { onSelect(it) },
                            border = if (it == current) BorderStroke(2.dp, Color.Blue) else null,
                        ) {
                            Text("$it")
                        }
                    }
                }
                OutlinedButton(onClick = onCreateRequest) {
                    Text(text = stringResource(R.string.create))
                }
            }
        }
    }
}