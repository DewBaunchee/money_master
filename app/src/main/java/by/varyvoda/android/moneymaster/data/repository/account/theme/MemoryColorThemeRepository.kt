package by.varyvoda.android.moneymaster.data.repository.account.theme

import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CopyOnWriteArrayList

class MemoryColorThemeRepository : ColorThemeRepository {

    private val themes: MutableList<ColorTheme> = CopyOnWriteArrayList()

    override suspend fun insert(colorTheme: ColorTheme) {
        themes.add(colorTheme)
    }

    override suspend fun update(colorTheme: ColorTheme) = TODO()

    override suspend fun delete(id: Id) = TODO()

    override fun getAll(): Flow<List<ColorTheme>> = flow { emit(themes.toList()) }
}