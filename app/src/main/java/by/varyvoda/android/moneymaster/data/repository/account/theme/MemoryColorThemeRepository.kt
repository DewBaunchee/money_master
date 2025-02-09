package by.varyvoda.android.moneymaster.data.repository.account.theme

import androidx.compose.ui.graphics.Color
import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CopyOnWriteArrayList

class MemoryColorThemeRepository : ColorThemeRepository {

    private val themes = CopyOnWriteArrayList<ColorTheme>().apply {
        addAll(
            listOf(
                ColorTheme(
                    name = "Gray",
                    colors = listOf(Color(0xFF202335), Color(0xFF5E669B))
                ),
                ColorTheme(
                    name = "Blue",
                    colors = listOf(Color(0xFF0253F4), Color(0xFFA228FF))
                ),
                ColorTheme(
                    name = "Purple",
                    colors = listOf(Color(0xFF6302F4), Color(0xFFA228FF))
                ),
                ColorTheme(
                    name = "Pink",
                    colors = listOf(Color(0xFFF40202), Color(0xFFA228FF))
                ),
                ColorTheme(
                    name = "Orange",
                    colors = listOf(Color(0xFFFF5E36), Color(0xFFFF282C))
                )
            )
        )
    }

    override suspend fun insert(colorTheme: ColorTheme) {
        themes.add(colorTheme)
    }

    override suspend fun update(colorTheme: ColorTheme) = TODO()

    override suspend fun delete(id: Id) = TODO()

    override fun getAll(): Flow<List<ColorTheme>> = flow { emit(themes.toList()) }
}