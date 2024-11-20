package by.varyvoda.android.moneymaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.varyvoda.android.moneymaster.ui.theme.MoneyMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoneyMasterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MoneyMasterApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MoneyMasterApp(modifier: Modifier = Modifier) {

}
