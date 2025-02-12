package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TitleAndText(title: String, text: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        AppTitle(text = title)
        Text(text = text)
    }
}
