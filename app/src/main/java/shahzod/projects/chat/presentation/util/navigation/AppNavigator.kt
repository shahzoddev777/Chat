package shahzod.projects.chat.presentation.util.navigation

import cafe.adriel.voyager.core.screen.Screen

interface AppNavigator {
    fun navigateTo(screen: Screen)
    fun back()
    fun replace(screen: Screen)
    fun replaceAll(screen: Screen)
}
