package by.varyvoda.android.moneymaster.config

import android.content.Context
import by.varyvoda.android.moneymaster.config.database.AppRoomDatabase
import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountBalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountIncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountMutationCategoryDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.mutation.AccountTransferDao
import by.varyvoda.android.moneymaster.data.dao.account.theme.AccountThemeDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.RoomAccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.mutation.AccountMutationRepository
import by.varyvoda.android.moneymaster.data.repository.account.mutation.RoomAccountMutationRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.AccountThemeRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.RoomAccountThemeRepository
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyRepository
import by.varyvoda.android.moneymaster.data.repository.currency.RoomCurrencyRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.data.service.account.AccountServiceImpl
import by.varyvoda.android.moneymaster.ui.screen.account.addincome.AddIncomeViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountCreationViewModel
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
    bind<AccountMutationRepository>() with singleton {
        RoomAccountMutationRepository(instance(), instance(), instance())
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
    bind<AccountCreationViewModel>() with singleton {
        AccountCreationViewModel(instance())
    }
    bind<AddIncomeViewModel>() with singleton {
        AddIncomeViewModel(instance(), instance())
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
        instance<AppRoomDatabase>().currency()
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
    bind<AccountMutationCategoryDao>() with singleton {
        instance<AppRoomDatabase>().accountMutationCategoryDao()
    }
}