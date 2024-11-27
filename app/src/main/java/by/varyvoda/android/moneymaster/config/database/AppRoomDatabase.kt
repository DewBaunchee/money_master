package by.varyvoda.android.moneymaster.config.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountBalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountIncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountMutationCategoryDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountTransferDao
import by.varyvoda.android.moneymaster.data.dao.account.theme.AccountThemeDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountBalanceEdit
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountIncome
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountMutationCategory
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountExpense
import by.varyvoda.android.moneymaster.data.model.account.mutation.AccountTransfer
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
        AccountMutationCategory::class,
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(ColorConverters::class, ColorListConverters::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun currency(): CurrencyDao

    abstract fun accountDao(): AccountDao

    abstract fun accountThemeDao(): AccountThemeDao

    abstract fun accountBalanceEditDao(): AccountBalanceEditDao

    abstract fun accountIncomeDao(): AccountIncomeDao

    abstract fun accountExpenseDao(): AccountExpenseDao

    abstract fun accountTransferDao(): AccountTransferDao

    abstract fun accountMutationCategoryDao(): AccountMutationCategoryDao

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