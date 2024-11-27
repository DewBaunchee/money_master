package by.varyvoda.android.moneymaster.data.repository.account.theme

import by.varyvoda.android.moneymaster.data.dao.account.theme.AccountThemeDao
import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

class RoomAccountThemeRepository(
    private val accountThemeDao: AccountThemeDao
) : AccountThemeRepository {

    override suspend fun insert(accountTheme: AccountTheme) = accountThemeDao.insert(accountTheme)

    override suspend fun update(accountTheme: AccountTheme) = accountThemeDao.update(accountTheme)

    override suspend fun delete(id: Id) = accountThemeDao.deleteById(id)

    override fun getById(id: Id): Flow<AccountTheme?> = accountThemeDao.getById(id)

    override fun getAll(): Flow<List<AccountTheme>> = accountThemeDao.getAll()
}