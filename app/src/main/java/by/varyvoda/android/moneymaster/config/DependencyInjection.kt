package by.varyvoda.android.moneymaster.config

import android.content.Context
import by.varyvoda.android.moneymaster.config.database.AppRoomDatabase
import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountBalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountIncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountOperationCategoryDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountTransferDao
import by.varyvoda.android.moneymaster.data.dao.account.theme.AccountThemeDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.RoomAccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.AccountOperationRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.RoomAccountOperationRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.AccountOperationCategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.RoomAccountOperationCategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.AccountThemeRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.RoomAccountThemeRepository
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyRepository
import by.varyvoda.android.moneymaster.data.repository.currency.RoomCurrencyRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.data.service.account.AccountServiceImpl
import by.varyvoda.android.moneymaster.ui.screen.account.addincome.AddIncomeViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseViewModel
import by.varyvoda.android.moneymaster.ui.screen.home.HomeViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun appModule(context: Context) = DI.Module("appModule") {
    import(databaseModule(context))

    bind<CurrencyRepository>() with singleton {
        RoomCurrencyRepository(instance())
    }
    bind<AccountThemeRepository>() with singleton {
        RoomAccountThemeRepository(instance())
    }
    bind<AccountOperationRepository>() with singleton {
        RoomAccountOperationRepository(instance(), instance(), instance())
    }
    bind<AccountOperationCategoryRepository>() with singleton {
        RoomAccountOperationCategoryRepository(instance())
    }
    bind<AccountRepository>() with singleton {
        RoomAccountRepository(instance())
    }
    bind<AccountService>() with singleton {
        AccountServiceImpl(instance(), instance())
    }

    import(viewModelModule())
}

fun viewModelModule() = DI.Module("viewModelModule") {
    bind<HomeViewModel>() with singleton {
        HomeViewModel(instance())
    }
    bind<AccountEditViewModel>() with singleton {
        AccountEditViewModel(instance(), instance(), instance())
    }
    bind<AddIncomeViewModel>() with singleton {
        AddIncomeViewModel(instance(), instance(), instance())
    }
    bind<AddExpenseViewModel>() with singleton {
        AddExpenseViewModel(instance(), instance())
    }
}


fun databaseModule(context: Context) = DI.Module("databaseModule") {
    bind<AppRoomDatabase>() with singleton {
        AppRoomDatabase.createDatabase(context)
    }
    bind<CurrencyDao>() with singleton {
        instance<AppRoomDatabase>().currencyDao()
    }
    bind<AccountDao>() with singleton {
        instance<AppRoomDatabase>().accountDao()
    }
    bind<AccountThemeDao>() with singleton {
        instance<AppRoomDatabase>().accountThemeDao()
    }
    bind<AccountBalanceEditDao>() with singleton {
        instance<AppRoomDatabase>().accountBalanceEditDao()
    }
    bind<AccountIncomeDao>() with singleton {
        instance<AppRoomDatabase>().accountIncomeDao()
    }
    bind<AccountExpenseDao>() with singleton {
        instance<AppRoomDatabase>().accountExpenseDao()
    }
    bind<AccountTransferDao>() with singleton {
        instance<AppRoomDatabase>().accountTransferDao()
    }
    bind<AccountOperationCategoryDao>() with singleton {
        instance<AppRoomDatabase>().accountOperationCategoryDao()
    }
}