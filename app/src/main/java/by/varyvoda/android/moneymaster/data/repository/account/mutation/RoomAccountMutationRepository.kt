package by.varyvoda.android.moneymaster.data.repository.account.mutation

import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountBalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountIncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountExpenseDao
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class RoomAccountMutationRepository(
    private val accountBalanceEditDao: AccountBalanceEditDao,
    private val accountIncomeDao: AccountIncomeDao,
    private val accountExpenseDao: AccountExpenseDao,
) : AccountMutationRepository {

    private val mutationTypeToDao: Map<AccountOperation.Type, BaseDao<out AccountOperation>> = mapOf(
        AccountOperation.Type.BALANCE_EDIT to accountBalanceEditDao,
        AccountOperation.Type.INCOME to accountIncomeDao,
        AccountOperation.Type.EXPENSE to accountExpenseDao,
    )

    override suspend fun insert(accountMutation: AccountOperation) {
        getDaoFor(accountMutation).insert(accountMutation)
    }

    override fun getAccountMutationsByAccountId(accountId: Id): Flow<List<AccountOperation>> {
        return combine(
            accountBalanceEditDao.getByAccountId(accountId),
            accountIncomeDao.getByAccountId(accountId),
            accountExpenseDao.getByAccountId(accountId),
        ) { (balanceEdits, incomes, expenses) ->
            return@combine balanceEdits.plus(incomes).plus(expenses)
                .sortedBy { it.date }
        }
    }

    private fun getDaoFor(mutation: AccountOperation): BaseDao<AccountOperation> {
        return mutationTypeToDao.getOrElse(mutation.type) {
            throw IllegalArgumentException("Cannot find DAO for ${mutation.type}")
        } as BaseDao<AccountOperation>
    }
}