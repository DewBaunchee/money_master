package by.varyvoda.android.moneymaster.config

import android.content.Context
import by.varyvoda.android.moneymaster.config.database.AppRoomDatabase
import by.varyvoda.android.moneymaster.data.converter.RoomIconConverter
import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountBalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountIncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountOperationCategoryDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.AccountTransferDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.RoomAccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.AccountOperationRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.RoomAccountOperationRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.AccountOperationCategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.RoomAccountOperationCategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.ColorThemeRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.MemoryColorThemeRepository
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyRepository
import by.varyvoda.android.moneymaster.data.repository.currency.RoomCurrencyRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.data.service.account.AccountServiceImpl
import by.varyvoda.android.moneymaster.data.service.category.AccountOperationCategoryService
import by.varyvoda.android.moneymaster.data.service.category.AccountOperationCategoryServiceImpl
import by.varyvoda.android.moneymaster.data.service.icons.GoogleIconsService
import by.varyvoda.android.moneymaster.data.service.icons.IconsService
import by.varyvoda.android.moneymaster.ui.screen.account.addincome.AddIncomeViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.category.AccountOperationCategoryEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.creation.AccountEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.expense.AddExpenseViewModel
import by.varyvoda.android.moneymaster.ui.screen.home.HomeViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun appModule(context: Context) = DI.Module("appModule") {
    bind<IconsService>() with singleton {
        GoogleIconsService(context.assets)
    }
    bind<RoomIconConverter>() with singleton {
        RoomIconConverter(instance())
    }

    import(databaseModule(context))

    bind<CurrencyRepository>() with singleton {
        RoomCurrencyRepository(instance())
    }
    bind<ColorThemeRepository>() with singleton {
        MemoryColorThemeRepository()
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
    bind<AccountOperationCategoryService>() with singleton {
        AccountOperationCategoryServiceImpl(instance())
    }

    import(viewModelModule())
}

fun viewModelModule() = DI.Module("viewModelModule") {
    bind<HomeViewModel>() with singleton {
        HomeViewModel(instance())
    }
    bind<AccountEditViewModel>() with singleton {
        AccountEditViewModel(instance(), instance(), instance(), instance())
    }
    bind<AddIncomeViewModel>() with singleton {
        AddIncomeViewModel(instance(), instance(), instance())
    }
    bind<AddExpenseViewModel>() with singleton {
        AddExpenseViewModel(instance(), instance())
    }
    bind<AccountOperationCategoryEditViewModel>() with singleton {
        AccountOperationCategoryEditViewModel(instance(), instance(), instance())
    }
}


fun databaseModule(context: Context) = DI.Module("databaseModule") {
    bind<AppRoomDatabase>() with singleton {
        AppRoomDatabase.createDatabase(context, instance())
    }
    bind<CurrencyDao>() with singleton {
        instance<AppRoomDatabase>().currencyDao()
    }
    bind<AccountDao>() with singleton {
        instance<AppRoomDatabase>().accountDao()
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