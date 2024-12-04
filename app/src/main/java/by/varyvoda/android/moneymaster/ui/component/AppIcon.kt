package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

@Composable
fun AppIcon(
    iconRef: IconRef,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary,
) {
    Icon(
        imageVector = rememberIconRef(iconRef),
        contentDescription = iconRef.label,
        modifier = modifier,
        tint = tint,
    )
}