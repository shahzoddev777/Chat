package shahzod.projects.chat.presentation.util.navigation

import androidx.lifecycle.LiveData

interface AppNavigationHandler {
    val backStack : LiveData<AppNavigationParam>

}