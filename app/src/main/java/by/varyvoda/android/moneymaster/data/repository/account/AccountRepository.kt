package by.varyvoda.android.moneymaster.data.repository.account

import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.AccountDetails
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun insert(account: Account)

    suspend fun update(account: Account)

    suspend fun delete(id: Id)

    fun getById(id: Id): Flow<Account?>

    fun getAll(): Flow<List<Account>>

    fun getDetailsById(id: Id): Flow<AccountDetails?>

    fun getAllDetails(): Flow<List<AccountDetails>>
}