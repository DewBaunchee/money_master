package by.varyvoda.android.moneymaster.data.repository.account.operation

import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.BalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.ExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.IncomeDao
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class RoomOperationRepository(
    private val balanceEditDao: BalanceEditDao,
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
) : OperationRepository {

    private val operationTypeToDao: Map<Operation.Type, BaseDao<out Operation>> = mapOf(
        Operation.Type.BALANCE_EDIT to balanceEditDao,
        Operation.Type.INCOME to incomeDao,
        Operation.Type.EXPENSE to expenseDao,
    )

    override suspend fun insert(operation: Operation) {
        getDaoFor(operation).insert(operation)
    }

    override fun getOperationsByAccountId(accountId: Id): Flow<List<Operation>> {
        return combine(
            balanceEditDao.getByAccountId(accountId),
            incomeDao.getByAccountId(accountId),
            expenseDao.getByAccountId(accountId),
        ) { (balanceEdits, incomes, expenses) ->
            return@combine balanceEdits.plus(incomes).plus(expenses)
                .sortedBy { it.date }
        }
    }

    private fun getDaoFor(operation: Operation): BaseDao<Operation> {
        return operationTypeToDao.getOrElse(operation.type) {
            throw IllegalArgumentException("Cannot find DAO for ${operation.type}")
        } as BaseDao<Operation>
    }
}