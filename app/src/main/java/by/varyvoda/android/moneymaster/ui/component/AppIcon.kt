package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

@Composable
fun AppIcon(
    iconRef: IconRef? = null,
    imageVector: ImageVector? = iconRef?.let { rememberIconRef(it) },
    contentDescription: String? = iconRef?.label,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary,
) {
    if (imageVector == null) return

    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}