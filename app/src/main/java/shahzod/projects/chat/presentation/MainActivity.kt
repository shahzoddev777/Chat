package shahzod.projects.chat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import shahzod.projects.chat.presentation.auth.PhoneScreen
import shahzod.projects.chat.presentation.ui.theme.ChatTheme
import shahzod.projects.chat.presentation.util.navigation.AppNavigationHandler
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appNavigationHandler: AppNavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatTheme {
                Navigator(PhoneScreen()) { navigator ->
                    val backstack = appNavigationHandler.backStack.observeAsState().value

                    LaunchedEffect(backstack) {
                        backstack?.invoke(navigator)
                    }

                    CurrentScreen()
                }
            }
        }
    }
}
