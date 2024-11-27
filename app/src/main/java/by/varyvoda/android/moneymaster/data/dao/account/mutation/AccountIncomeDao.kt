package by.varyvoda.android.moneymaster.data.dao.account.mutation

import androidx.room.Dao
import androidx.room.Query
import by.varyvoda.android.moneymaster.data.dao.BaseDao
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountIncome
import by.varyvoda.android.moneymaster.data.model.domain.Id
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountIncomeDao : BaseDao<AccountIncome> {

    @Query("DELETE FROM account_income WHERE id = :id")
    suspend fun deleteById(id: Id)

    @Query("SELECT * FROM account_income WHERE id = :id")
    fun getById(id: Id): Flow<AccountIncome>

    @Query("SELECT * FROM account_income")
    fun getAll(): Flow<List<AccountIncome>>

    @Query("SELECT * FROM account_income WHERE accountId = :accountId")
    fun getByAccountId(accountId: Id): Flow<List<AccountIncome>>
}