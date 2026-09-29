package shahzod.projects.chat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.asFlow
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import shahzod.projects.chat.presentation.auth.PhoneScreen
import shahzod.projects.chat.presentation.home.HomeScreen
import shahzod.projects.chat.presentation.ui.theme.ChatTheme
import shahzod.projects.core.local.LocalDataStorage
import shahzod.projects.presentation.util.navigation.AppNavigationHandler
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigationHandler: AppNavigationHandler
    @Inject
    lateinit var localStorage: LocalDataStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val startScreen = if (localStorage.isSigned) HomeScreen() else PhoneScreen()
        setContent {
            ChatTheme {
                Navigator(startScreen) { navigator ->
                    val backstack =
                        appNavigationHandler.backStack.asFlow().collectAsState(initial = null).value

                    LaunchedEffect(backstack) {
                        backstack?.invoke(navigator)
                    }

                    CurrentScreen()
                }
            }
        }
    }
}
