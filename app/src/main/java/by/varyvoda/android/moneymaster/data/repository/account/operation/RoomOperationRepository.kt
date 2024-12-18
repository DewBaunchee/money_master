package by.varyvoda.android.moneymaster.data.repository.account.operation

import by.varyvoda.android.moneymaster.data.dao.account.operation.BalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.ExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.IncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.OperationDao
import by.varyvoda.android.moneymaster.data.details.account.operation.BalanceEditDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.ExpenseDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.IncomeDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.OperationDetails
import by.varyvoda.android.moneymaster.data.details.account.operation.TransferDetails
import by.varyvoda.android.moneymaster.data.model.account.operation.BalanceEdit
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.account.operation.Operation
import by.varyvoda.android.moneymaster.data.model.account.operation.Transfer
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.CategoryRepository
import by.varyvoda.android.moneymaster.util.anyNotNull
import by.varyvoda.android.moneymaster.util.notNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.UUID

class RoomOperationRepository(
    private val balanceEditDao: BalanceEditDao,
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
) : OperationRepository {

    private val daoProvider = OperationDaoProvider(balanceEditDao, incomeDao, expenseDao)

    override suspend fun insert(operation: Operation) =
        daoProvider.getDaoFor(operation).insert(operation)

    override suspend fun update(operation: Operation) =
        daoProvider.getDaoFor(operation).update(operation)

    override suspend fun delete(id: UUID, operationType: Operation.Type?) {
        allDaoRun { it.deleteById(id) }
    }

    override fun getById(id: UUID): Flow<Operation?> =
        combineDaoResults { it.getById(id) }.map { it.singleNotNullOperation() }

    override fun getAll(): Flow<List<Operation>> =
        combineDaoResults { it.getAll() }.map { combineLists(it) }

    override fun getDetailsById(id: UUID): Flow<OperationDetails?> =
        getById(id).map { it?.details() }

    override fun getAllDetails(): Flow<List<OperationDetails>> =
        getAll().map { list -> list.map { it.details() } }

    private fun Operation.details(): OperationDetails = when (this) {
        is BalanceEdit -> details()
        is Expense -> details()
        is Income -> details()
        is Transfer -> details()
        else -> throw IllegalArgumentException("Cannot create details for operation $this")
    }

    private fun BalanceEdit.details(): BalanceEditDetails =
        BalanceEditDetails(this)

    private fun Expense.details(): ExpenseDetails =
        ExpenseDetails(
            model = this,
            category = categoryRepository.getById(categoryId).notNull(),
            accountDetails = accountRepository.getDetailsById(accountId).notNull()
        )

    private fun Income.details(): IncomeDetails =
        IncomeDetails(
            model = this,
            category = categoryRepository.getById(categoryId).notNull(),
            accountDetails = accountRepository.getDetailsById(accountId).notNull()
        )

    private fun Transfer.details(): TransferDetails =
        TransferDetails(this)

    private inline fun <reified R> allDaoRun(action: (OperationDao<out Operation>) -> R) =
        arrayOf(
            action(balanceEditDao),
            action(expenseDao),
            action(incomeDao),
        )

    private inline fun <reified R> combineDaoResults(action: (OperationDao<out Operation>) -> Flow<R>) =
        combine(
            action(balanceEditDao),
            action(expenseDao),
            action(incomeDao),
            transform = ::arrayOf,
        )

    private fun combineLists(lists: Array<List<Operation>>): List<Operation> =
        lists.reduce { left, right -> left.plus(right) }
            .sortedBy { it.date }

    private fun Array<Operation?>.singleNotNullOperation() =
        takeIf { anyNotNull(it) }?.single { it != null }
}

private class OperationDaoProvider(
    balanceEditDao: BalanceEditDao,
    incomeDao: IncomeDao,
    expenseDao: ExpenseDao,
) {

    private val operationTypeToDao: Map<Operation.Type, OperationDao<out Operation>> = mapOf(
        Operation.Type.BALANCE_EDIT to balanceEditDao,
        Operation.Type.INCOME to incomeDao,
        Operation.Type.EXPENSE to expenseDao,
    )

    fun getDaoFor(operation: Operation): OperationDao<Operation> =
        getDaoFor(operation.type)

    @Suppress("UNCHECKED_CAST")
    fun getDaoFor(operationType: Operation.Type): OperationDao<Operation> =
        operationTypeToDao.getOrElse(operationType) {
            throw IllegalArgumentException("Cannot find DAO for $operationType")
        } as OperationDao<Operation>
}
