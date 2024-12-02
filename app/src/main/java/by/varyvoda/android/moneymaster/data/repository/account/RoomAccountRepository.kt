package by.varyvoda.android.moneymaster.data.repository.account

import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

class RoomAccountRepository(
    private val accountDao: AccountDao
) : AccountRepository {

    override suspend fun insert(accountInfo: Account) = accountDao.insert(accountInfo)

    override suspend fun update(accountInfo: Account) = accountDao.update(accountInfo)

    override suspend fun delete(id: Id) = accountDao.deleteById(id)

    override fun getById(id: Id): Flow<Account?> = accountDao.getById(id)

    override fun getAll(): Flow<List<Account>> = accountDao.getAll()

    override fun getDetailsById(id: Id): Flow<AccountDetails?> = accountDao.getDetailsById(id)

    override fun getAllDetails(): Flow<List<AccountDetails>> = accountDao.getAllDetails()
}