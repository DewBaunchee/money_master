package by.varyvoda.android.moneymaster.data.repository.account

import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyRepository
import by.varyvoda.android.moneymaster.util.notNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomAccountRepository(
    private val accountDao: AccountDao,
    private val currencyRepository: CurrencyRepository,
) : AccountRepository {

    override suspend fun upsert(account: Account) = accountDao.upsert(account)

    override suspend fun updateBalance(accountId: Id, balance: MoneyAmount) =
        accountDao.updateBalance(accountId, balance)

    override suspend fun delete(id: Id) = accountDao.deleteById(id)

    override fun getById(id: Id): Flow<Account?> = accountDao.getById(id)

    override fun getAll(): Flow<List<Account>> = accountDao.getAll()

    override fun getDetailsById(id: Id): Flow<AccountDetails?> = getById(id).map { it?.details() }

    override fun getAllDetails(): Flow<List<AccountDetails>> =
        getAll().map { list -> list.map { it.details() } }

    private fun Account.details() =
        AccountDetails(
            model = this,
            currency = currencyRepository.getByCode(currencyCode).notNull()
        )
}