package by.varyvoda.android.moneymaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import by.varyvoda.android.moneymaster.config.initializeDependencies
import by.varyvoda.android.moneymaster.config.appModule
import by.varyvoda.android.moneymaster.config.database.AppRoomDatabase
import by.varyvoda.android.moneymaster.config.database.initializeDatabase
import by.varyvoda.android.moneymaster.ui.MoneyMasterApp
import by.varyvoda.android.moneymaster.ui.theme.MoneyMasterTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DI.Companion.invoke
import org.kodein.di.compose.withDI
import org.kodein.di.instance

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val di = DI {
            import(appModule(applicationContext))
        }

        GlobalScope.launch {
            val database: AppRoomDatabase by di.instance()
            initializeDatabase(database)
            initializeDependencies(di)
        }

        setContent {
            MoneyMasterTheme {
                withDI(di) { MoneyMasterApp() }
            }
        }
    }
}
