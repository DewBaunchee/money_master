package by.varyvoda.android.moneymaster.config.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountBalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountIncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountOperationCategoryDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountTransferDao
import by.varyvoda.android.moneymaster.data.dao.account.theme.AccountThemeDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountBalanceEdit
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountExpense
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountIncome
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountOperationCategory
import by.varyvoda.android.moneymaster.data.model.account.operation.AccountTransfer
import by.varyvoda.android.moneymaster.data.model.account.theme.AccountTheme
import by.varyvoda.android.moneymaster.data.model.currency.Currency

@Database(
    entities = [
        Currency::class,
        Account::class,
        AccountTheme::class,
        AccountBalanceEdit::class,
        AccountIncome::class,
        AccountExpense::class,
        AccountTransfer::class,
        AccountOperationCategory::class,
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(ColorConverters::class, ColorListConverters::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun accountDao(): AccountDao

    abstract fun accountThemeDao(): AccountThemeDao

    abstract fun accountBalanceEditDao(): AccountBalanceEditDao

    abstract fun accountIncomeDao(): AccountIncomeDao

    abstract fun accountExpenseDao(): AccountExpenseDao

    abstract fun accountTransferDao(): AccountTransferDao

    abstract fun accountOperationCategoryDao(): AccountOperationCategoryDao

    companion object {

        fun createDatabase(context: Context): AppRoomDatabase {
            return Room.databaseBuilder(
                context,
                AppRoomDatabase::class.java,
                "moneymaster_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}