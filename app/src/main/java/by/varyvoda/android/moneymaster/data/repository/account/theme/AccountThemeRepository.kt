package by.varyvoda.android.moneymaster.data.repository.account.theme

import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

interface AccountThemeRepository {

    suspend fun insert(accountTheme: AccountTheme)

    suspend fun update(accountTheme: AccountTheme)

    suspend fun delete(id: Id)

    fun getById(id: Id): Flow<AccountTheme?>

    fun getAll(): Flow<List<AccountTheme>>
}