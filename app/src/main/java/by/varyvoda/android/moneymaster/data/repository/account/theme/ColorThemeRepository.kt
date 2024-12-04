package by.varyvoda.android.moneymaster.data.repository.account.theme

import by.varyvoda.android.moneymaster.data.model.account.theme.ColorTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

interface ColorThemeRepository {

    suspend fun insert(colorTheme: ColorTheme)

    suspend fun update(colorTheme: ColorTheme)

    suspend fun delete(id: Id)

    fun getAll(): Flow<List<ColorTheme>>
}