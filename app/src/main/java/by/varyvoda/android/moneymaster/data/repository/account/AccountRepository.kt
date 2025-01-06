package by.varyvoda.android.moneymaster.data.repository.account

import by.varyvoda.android.moneymaster.data.details.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.MoneyAmount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun insert(account: Account)

    suspend fun update(account: Account)

    suspend fun updateBalance(accountId: Id, balance: MoneyAmount)

    suspend fun delete(id: Id)

    fun getById(id: Id): Flow<Account?>

    fun getAll(): Flow<List<Account>>

    fun getDetailsById(id: Id): Flow<AccountDetails?>

    fun getAllDetails(): Flow<List<AccountDetails>>
}