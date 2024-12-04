package by.varyvoda.android.moneymaster.data.model.account.theme

import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.model.domain.GradientColors
import java.util.Objects

data class ColorTheme(
    val name: String,
    val colors: GradientColors,
) {

    companion object {
        val DEFAULT = ColorTheme("Default", listOf(Color.Black, Color.Black))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is ColorTheme) return false
        return name == other.name && colors == other.colors
    }

    override fun hashCode(): Int {
        return Objects.hash(name, colors)
    }
}
