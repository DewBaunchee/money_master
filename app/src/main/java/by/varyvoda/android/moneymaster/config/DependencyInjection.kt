package by.varyvoda.android.moneymaster.config

import android.content.Context
import by.varyvoda.android.moneymaster.config.database.AppRoomDatabase
import by.varyvoda.android.moneymaster.data.converter.RoomIconConverter
import by.varyvoda.android.moneymaster.data.dao.account.AccountDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.BalanceEditDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.CategoryDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.ExpenseDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.IncomeDao
import by.varyvoda.android.moneymaster.data.dao.account.operation.TransferDao
import by.varyvoda.android.moneymaster.data.dao.currency.CurrencyDao
import by.varyvoda.android.moneymaster.data.repository.account.AccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.RoomAccountRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.OperationRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.RoomOperationRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.CategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.operation.category.RoomCategoryRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.ColorThemeRepository
import by.varyvoda.android.moneymaster.data.repository.account.theme.MemoryColorThemeRepository
import by.varyvoda.android.moneymaster.data.repository.currency.CurrencyRepository
import by.varyvoda.android.moneymaster.data.repository.currency.RoomCurrencyRepository
import by.varyvoda.android.moneymaster.data.service.account.AccountService
import by.varyvoda.android.moneymaster.data.service.account.AccountServiceImpl
import by.varyvoda.android.moneymaster.data.service.category.CategoryService
import by.varyvoda.android.moneymaster.data.service.category.CategoryServiceImpl
import by.varyvoda.android.moneymaster.data.service.icons.GoogleIconsService
import by.varyvoda.android.moneymaster.data.service.icons.IconsService
import by.varyvoda.android.moneymaster.ui.screen.account.category.CategoryEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.edit.AccountEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.account.operation.edit.OperationEditViewModel
import by.varyvoda.android.moneymaster.ui.screen.home.HomeViewModel
import by.varyvoda.android.moneymaster.ui.screen.more.MoreViewModel
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
    bind<OperationRepository>() with singleton {
        RoomOperationRepository(instance(), instance(), instance(), instance(), instance())
    }
    bind<CategoryRepository>() with singleton {
        RoomCategoryRepository(instance())
    }
    bind<AccountRepository>() with singleton {
        RoomAccountRepository(instance(), instance())
    }
    bind<AccountService>() with singleton {
        AccountServiceImpl(instance(), instance())
    }
    bind<CategoryService>() with singleton {
        CategoryServiceImpl(instance())
    }

    import(viewModelModule())
}

fun viewModelModule() = DI.Module("viewModelModule") {
    bind<HomeViewModel>() with singleton {
        HomeViewModel(instance(), instance())
    }
    bind<MoreViewModel>() with singleton {
        MoreViewModel()
    }

    bind<AccountEditViewModel>() with singleton {
        AccountEditViewModel(instance(), instance(), instance(), instance())
    }
    bind<OperationEditViewModel>() with singleton {
        OperationEditViewModel(instance(), instance(), instance())
    }
    bind<CategoryEditViewModel>() with singleton {
        CategoryEditViewModel(instance(), instance(), instance())
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
    bind<BalanceEditDao>() with singleton {
        instance<AppRoomDatabase>().accountBalanceEditDao()
    }
    bind<IncomeDao>() with singleton {
        instance<AppRoomDatabase>().accountIncomeDao()
    }
    bind<ExpenseDao>() with singleton {
        instance<AppRoomDatabase>().accountExpenseDao()
    }
    bind<TransferDao>() with singleton {
        instance<AppRoomDatabase>().accountTransferDao()
    }
    bind<CategoryDao>() with singleton {
        instance<AppRoomDatabase>().accountOperationCategoryDao()
    }
}