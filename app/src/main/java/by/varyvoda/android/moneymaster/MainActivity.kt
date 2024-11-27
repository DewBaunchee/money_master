package by.varyvoda.android.moneymaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import by.varyvoda.android.moneymaster.ui.MoneyMasterApp
import by.varyvoda.android.moneymaster.ui.theme.MoneyMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyMasterTheme {
                MoneyMasterApp()
            }
        }
    }
}
