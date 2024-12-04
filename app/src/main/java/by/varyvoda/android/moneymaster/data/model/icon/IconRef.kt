package by.varyvoda.android.moneymaster.data.model.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SettingsInputSvideo
import androidx.compose.ui.graphics.vector.ImageVector

class IconRef(
    val name: String,
    val label: String,
    val category: String,
    private val loader: suspend () -> ImageVector?,
) {

    companion object {

        fun ImageVector.toIconRef(label: String = name) = IconRef(
            name = name,
            label = label,
            category = root.name,
            loader = { this },
        )

        val DEFAULT_VECTOR = Icons.Filled.SettingsInputSvideo
        val DEFAULT = IconRef(
            name = "Default",
            label = "Default",
            category = "Default",
            loader = { DEFAULT_VECTOR }
        )
    }

    suspend fun load() = loader()
}