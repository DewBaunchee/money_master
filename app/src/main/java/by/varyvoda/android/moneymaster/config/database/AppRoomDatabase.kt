package by.varyvoda.android.moneymaster.config.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.varyvoda.android.moneymaster.data.converter.ColorConverters
import by.varyvoda.android.moneymaster.data.converter.ColorListConverters
import by.varyvoda.android.moneymaster.data.converter.ColorThemeConverters
import by.varyvoda.android.moneymaster.data.converter.RoomIconConverter
import by.varyvoda.android.moneymaster.data.converter.UUIDConverters
import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.BalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.CategoryDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.ExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.IncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.TransferDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.model.account.Account
import by.varyvoda.android.moneymaster.data.model.account.operation.BalanceEdit
import by.varyvoda.android.moneymaster.data.model.account.operation.Category
import by.varyvoda.android.moneymaster.data.model.account.operation.Expense
import by.varyvoda.android.moneymaster.data.model.account.operation.Income
import by.varyvoda.android.moneymaster.data.model.account.operation.Transfer
import by.varyvoda.android.moneymaster.data.model.currency.Currency

@Database(
    entities = [
        Currency::class,
        Account::class,
        BalanceEdit::class,
        Income::class,
        Expense::class,
        Transfer::class,
        Category::class,
    ],
    version = 12,
    exportSchema = false
)
@TypeConverters(
    ColorConverters::class,
    ColorListConverters::class,
    ColorThemeConverters::class,
    RoomIconConverter::class,
    UUIDConverters::class,
)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun accountDao(): AccountDao

    abstract fun accountBalanceEditDao(): BalanceEditDao

    abstract fun accountIncomeDao(): IncomeDao

    abstract fun accountExpenseDao(): ExpenseDao

    abstract fun accountTransferDao(): TransferDao

    abstract fun accountOperationCategoryDao(): CategoryDao

    companion object {

        fun createDatabase(
            context: Context,
            iconConverter: RoomIconConverter,
        ): AppRoomDatabase {
            return Room.databaseBuilder(
                context,
                AppRoomDatabase::class.java,
                "moneymaster_database"
            )
                .addTypeConverter(iconConverter)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}