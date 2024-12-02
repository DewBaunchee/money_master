package by.varyvoda.android.moneymaster.data.repository.account.operation

import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountBalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountIncomeDao
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class RoomAccountOperationRepository(
    private val accountBalanceEditDao: AccountBalanceEditDao,
    private val accountIncomeDao: AccountIncomeDao,
    private val accountExpenseDao: AccountExpenseDao,
) : AccountOperationRepository {

    private val operationTypeToDao: Map<AccountOperation.Type, BaseDao<out AccountOperation>> = mapOf(
        AccountOperation.Type.BALANCE_EDIT to accountBalanceEditDao,
        AccountOperation.Type.INCOME to accountIncomeDao,
        AccountOperation.Type.EXPENSE to accountExpenseDao,
    )

    override suspend fun insert(operation: AccountOperation) {
        getDaoFor(operation).insert(operation)
    }

    override fun getAccountOperationsByAccountId(accountId: Id): Flow<List<AccountOperation>> {
        return combine(
            accountBalanceEditDao.getByAccountId(accountId),
            accountIncomeDao.getByAccountId(accountId),
            accountExpenseDao.getByAccountId(accountId),
        ) { (balanceEdits, incomes, expenses) ->
            return@combine balanceEdits.plus(incomes).plus(expenses)
                .sortedBy { it.date }
        }
    }

    private fun getDaoFor(operation: AccountOperation): BaseDao<AccountOperation> {
        return operationTypeToDao.getOrElse(operation.type) {
            throw IllegalArgumentException("Cannot find DAO for ${operation.type}")
        } as BaseDao<AccountOperation>
    }
}