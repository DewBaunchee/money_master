package by.varyvoda.android.moneymaster.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import by.varyvoda.android.moneymaster.data.model.icon.IconRef

@Composable
fun rememberImageVector(
    fallbackVector: ImageVector,
    loader: suspend () -> ImageVector?,
): ImageVector {
    var imageVector by remember { mutableStateOf<ImageVector?>(null) }
    LaunchedEffect(loader) {
        imageVector = loader()
    }
    return imageVector ?: fallbackVector
}

@Composable
fun rememberIconRef(
    icon: IconRef,
    fallbackVector: ImageVector = IconRef.DEFAULT_VECTOR,
): ImageVector {
    return rememberImageVector(fallbackVector = fallbackVector) { icon.load() }
}