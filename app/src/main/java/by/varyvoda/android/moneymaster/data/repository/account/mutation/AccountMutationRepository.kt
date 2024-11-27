package by.varyvoda.android.moneymaster.data.repository.account.mutation

import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

interface AccountMutationRepository {

    suspend fun insert(accountMutation: AccountOperation)

    fun getAccountMutationsByAccountId(accountId: Id): Flow<List<AccountOperation>>
}