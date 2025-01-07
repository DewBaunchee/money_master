package by.varyvoda.android.moneymaster.data.repository.account.operation

import by.varyvoda.android.moneymaster.data.dao.account.operation.BalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.ExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.IncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.OperationDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.TransferDao
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
import by.varyvoda.android.moneymaster.data.model.domain.Id
import by.varyvoda.android.moneymaster.data.model.domain.PrimitiveDateRange
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
    private val transferDao: TransferDao,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
) : OperationRepository {

    private val daoProvider =
        OperationDaoProvider(balanceEditDao, incomeDao, expenseDao, transferDao)

    override suspend fun insert(operation: Operation) =
        daoProvider.getDaoFor(operation).insert(operation)

    override suspend fun update(operation: Operation) =
        daoProvider.getDaoFor(operation).update(operation)

    override suspend fun delete(operation: Operation) =
        daoProvider.getDaoFor(operation).delete(operation)

    override suspend fun delete(id: UUID) {
        allDaoRun { it.deleteById(id) }
    }

    override suspend fun delete(id: UUID, operationType: Operation.Type) {
        delete(id)
    }

    override fun getById(id: UUID): Flow<Operation?> =
        combineDaoResults { it.getById(id) }
            .map { it.singleNotNullOperation() }

    override fun getAll(): Flow<List<Operation>> =
        combineDaoResults { it.getAll() }
            .combineListsAndSort()

    override fun getDetailsById(id: UUID): Flow<OperationDetails?> =
        getById(id)
            .map { it?.details() }

    override fun getAllDetails(): Flow<List<OperationDetails>> =
        getAll()
            .mapToDetailsList()

    override fun getAllDetailsByAccountId(accountId: Id): Flow<List<OperationDetails>> =
        combineDaoResults { it.getByAccountId(accountId) }
            .combineListsAndSort()
            .mapToDetailsList()

    override fun getAllDetailsByTypeAndBetweenDateRange(
        operationType: Operation.Type,
        dateRange: PrimitiveDateRange
    ): Flow<List<OperationDetails>> =
        daoProvider.getDaoFor(operationType)
            .getAllBetween(from = dateRange.first, to = dateRange.second)
            .mapToDetailsList()

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
            account = accountRepository.getDetailsById(accountId).notNull()
        )

    private fun Income.details(): IncomeDetails =
        IncomeDetails(
            model = this,
            category = categoryRepository.getById(categoryId).notNull(),
            account = accountRepository.getDetailsById(accountId).notNull()
        )

    private fun Transfer.details(): TransferDetails =
        TransferDetails(
            model = this,
            sourceAccount = accountRepository.getDetailsById(sourceAccountId).notNull(),
            destinationAccount = accountRepository.getDetailsById(destinationAccountId).notNull(),
        )

    private fun Flow<Array<List<Operation>>>.combineListsAndSort() = map { combineLists(it).sort() }

    private fun Flow<List<Operation>>.mapToDetailsList() = map { list -> list.map { it.details() } }

    private fun List<Operation>.sort() = sortedByDescending { it.date }

    private inline fun <reified R> allDaoRun(action: (OperationDao<out Operation>) -> R) =
        arrayOf(
            action(balanceEditDao),
            action(expenseDao),
            action(incomeDao),
            action(transferDao),
        )

    private inline fun <reified R> combineDaoResults(action: (OperationDao<out Operation>) -> Flow<R>) =
        combine(
            action(balanceEditDao),
            action(expenseDao),
            action(incomeDao),
            action(transferDao),
            transform = ::arrayOf,
        )

    private fun combineLists(lists: Array<List<Operation>>) =
        lists.reduce { left, right -> left.plus(right) }

    private fun Array<Operation?>.singleNotNullOperation() =
        takeIf { anyNotNull(it) }?.single { it != null }
}

private class OperationDaoProvider(
    balanceEditDao: BalanceEditDao,
    incomeDao: IncomeDao,
    expenseDao: ExpenseDao,
    transferDao: TransferDao,
) {

    private val operationTypeToDao: Map<Operation.Type, OperationDao<out Operation>> = mapOf(
        Operation.Type.BALANCE_EDIT to balanceEditDao,
        Operation.Type.INCOME to incomeDao,
        Operation.Type.EXPENSE to expenseDao,
        Operation.Type.TRANSFER to transferDao,
    )

    fun getDaoFor(operation: Operation): OperationDao<Operation> =
        getDaoFor(operation.type)

    fun getDaoFor(operationType: Operation.Type): OperationDao<Operation> =
        operationTypeToDao.getOrThrowAndCast(operationType)

    @Suppress("UNCHECKED_CAST")
    private fun <K> Map<K, OperationDao<out Operation>>.getOrThrowAndCast(key: K) =
        getOrElse(key) {
            throw IllegalArgumentException("Cannot find DAO for $key")
        } as OperationDao<Operation>
}
