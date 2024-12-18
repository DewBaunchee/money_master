package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import by.varyvoda.android.moneymaster.R
import by.varyvoda.android.moneymaster.data.model.domain.toBrush
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

@Composable
fun SquareBox(
    background: Brush? = null,
    iconRef: IconRef? = null,
    tint: Color? = null,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .background(
                background ?: MaterialTheme.colorScheme.background.toBrush(),
                MaterialTheme.shapes.small
            )
            .size(dimensionResource(R.dimen.square_box_size)),
        contentAlignment = Alignment.Center,
    ) {
        if (iconRef != null) {
            AppIcon(
                iconRef = iconRef,
                tint = tint ?: Color.Unspecified,
                modifier = modifier.padding(dimensionResource(R.dimen.square_box_icon_padding)),
            )
        }
    }
}

@Composable
fun IconAndText(
    icon: @Composable RowScope.() -> Unit,
    text: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.icon_and_label_row_padding)),
        modifier = modifier,
    ) {
        icon()
        text()
    }
}